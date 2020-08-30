
import string
import easydict
import cv2
import torch
import torch.backends.cudnn as cudnn
import torch.utils.data
import torch.nn.functional as F
import Model.modules.tesseract_box as detect
from Model.modules.utils import CTCLabelConverter, AttnLabelConverter
from Model.modules.dataset import RawDataset, AlignCollate
from Model.modules.model import Model

device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')


def demo(model, converter, opt):

    # prepare data. two demo images from https://github.com/bgshih/crnn#run-demo
    AlignCollate_demo = AlignCollate(imgH=opt.imgH, imgW=opt.imgW, keep_ratio_with_pad=opt.PAD)
    demo_data = RawDataset(data=opt.img_data, opt=opt)  # use RawDataset
    demo_loader = torch.utils.data.DataLoader(
        demo_data, batch_size=opt.batch_size,
        shuffle=False,
        num_workers=int(opt.workers),
        collate_fn=AlignCollate_demo, pin_memory=True)

    # predict
    i_test_list =[]
    model.eval()
    with torch.no_grad():
        for image_info in demo_loader:
            image_tensors = image_info[0]
            image_line = image_info[1]
            image_wordH = image_info[2]
            
            batch_size = image_tensors.size(0)
            image = image_tensors

            # For max length prediction
            length_for_pred = torch.IntTensor([opt.batch_max_length] * batch_size).to(device)
            text_for_pred = torch.LongTensor(batch_size, opt.batch_max_length + 1).fill_(0).to(device)

            # 'ATTn' 모델 예측
            preds = model(image, text_for_pred, is_train=False)

            # select max probabilty (greedy decoding) then decode index to character
            _, preds_index = preds.max(2)

            preds_str = converter.decode(preds_index, length_for_pred)
            preds_prob = F.softmax(preds, dim=2)
            preds_max_prob, _ = preds_prob.max(dim=2)
            
            for pred, pred_max_prob,line, wordH in zip(preds_str, preds_max_prob, image_line, image_wordH):
                pred_EOS = pred.find('[s]')
                pred = pred[:pred_EOS]  # prune after "end of sentence" token ([s])
                pred_max_prob = pred_max_prob[:pred_EOS]

                # calculate confidence score (= multiply of pred_max_prob)
                confidence_score = pred_max_prob.cumprod(dim=0)[-1]
                i_test_list.append([pred+ " ", wordH, line])

    return i_test_list



def image_file(src, highlight):
  global opt, model, converter

  img_list = detect.tesseract_box(src, highlight)

  if highlight:
    img_list = detect.highlight_box_list(img_list)

  opt.img_data = img_list

  return demo(model, converter, opt)


opt = easydict.EasyDict({'FeatureExtraction': "ResNet",
       'PAD': False,
       'Prediction': "Attn",
       'SequenceModeling': "BiLSTM",
       'Transformation': "TPS",
       'batch_max_length': 25,
       'batch_size': 32,
       'character': "0123456789abcdefghijklmnopqrstuvwxyz",
       'hidden_size': 256,
       'imgH': 32,
       'imgW': 100,
       'input_channel': 1,
       'num_fiducial': 20,
       'output_channel': 512,
       'rgb': False,
       'saved_model': 'Model/TPS_ResNet_BiLSTM_Attn.pth',
       'sensitive': False,
       'workers': 4,
       'sensitive': False,
       'num_gpu': 0})


""" vocab / character number configuration """
if opt.sensitive:
    opt.character = string.printable[:-6]  # same with ASTER setting (use 94 char).

cudnn.benchmark = True
cudnn.deterministic = True
opt.num_gpu = torch.cuda.device_count()


converter = AttnLabelConverter(opt.character)
opt.num_class = len(converter.character)

if opt.rgb:
    opt.input_channel = 3
model = Model(opt)
model = torch.nn.DataParallel(model).to(device)

# load model
model.load_state_dict(torch.load(opt.saved_model, map_location=device))


