import cv2
import copy
import pytesseract
import os
from pytesseract import Output
from google.colab.patches import cv2_imshow
from collections import defaultdict

# tessercat box 치는 작업
def tesseract_box(path): ## path : img path
    img = cv2.imread(path)
    img_cl = copy.copy(img)
    d = pytesseract.image_to_data(img_cl,output_type=Output.DICT)
    ## dict_keys(['level', 'page_num', 'block_num', 'par_num', 'line_num', 'word_num', 'left', 'top', 'width', 'height', 'conf', 'text'])
    value_dict = list(d.values())
    # d 에 저장
    
# 인덱스 저장할 딕셔너리
    index_dict = defaultdict(int)

# bottom, left, top
    bottom = [ value_dict[7][i] + value_dict[9][i] for i in range(len(value_dict[7])) ]
    left = value_dict[6]
    top = value_dict[7]
    width = value_dict[8]
    wid = 0
    for i in width:
      if wid < i:
        wid = i

# (left, top, bottom) 만들기 위한 작업
    for idx, value in enumerate(zip(left, top, bottom)):
      if int(value_dict[10][idx]) > 60:
        index_dict[idx] = value


    order = []
# 순서 Sorting (top, left)
    for idx in sorted(index_dict.items(), key=lambda x: (x[1][1], x[1][0])):
        order.append(idx)

# 순서 줄 구분(Sorting)
    order_line = []
    line_num = 1
# order_line - 같은 라인인지 구분하여 Sorting
    temp = []
    for idx in range(1, len(order)):
  # 같은 라인일 때,
      if int(order[idx-1][1][2]) > int(order[idx][1][1]):
        temp.append((order[idx-1], line_num))
    # 마지막 라인 예외 처리 - order_line 마지막으로 푸쉬
        if len(order)-1 == idx:
          order_line.append(temp)

  # 다른 라인일 때,
      else:
        line_num+=1
        order_line.append(temp)
        temp = []
# left 기준으로 Sorting
    for idx in range(len(order_line)):
      order_line[idx].sort(key=lambda x: x[0][0])


# 최종 순서 저장
    index = []
    line = []
    for idx in order_line:
      for idx_ in idx:
        index.append([idx_[0][0],idx_[1]])
        
# 네모 치는 작업        
    n_boxes = len(d['text'])
    box_list = [] # box 저장소
    # left, right, top, bottom 
    merge_data = [] # 겹치는거 한번에 네모 치게끔하는 것

    val = 0 # 시작부분 저장하게끔하는 변수
    space = 10
    
    for idx in index:
        i = idx[0]
        line = idx[1]
        left, right= int(d['left'][i]), int(d['left'][i] + d['width'][i])
        top, bottom = int(d['top'][i]), int(d['top'][i] + d['height'][i])
        space = int(wid) / 100
        if val != 0 and left - merge_data[-1][1] < space and merge_data[-1][4] == line: ## 라인이 같고, 띄어쓰기가 올바르지 않을경우 합치기
            t = merge_data[-1]
            merge_data[-1] = (t[0], right, min(t[2], top), max(t[3], bottom),merge_data[-1][4],d['height'][i])
        else: ## 다를 경우 merge_data에 저장
            merge_data.append((left, right, top, bottom,line,d['height'][i]))
            print(left, right, top, bottom)
            print(d['text'][i])
            
        val += 1


    for t in merge_data: ## 네모 친 이미지를 box_list에 추가
        ##img_cl = cv2.rectangle(img_cl, (t[0], t[2]), (t[1], t[3]), (0, 255, 0), 2)   
        box_list.append((img_cl[t[2]:t[3],t[0]:t[1]], t[4],t[5]))
        
    count = 0
    for box in box_list:
        line_n = str(box[1]).zfill(2)
        counts = str(count).zfill(4)
        wordsize = str(box[2])
        cv2.imwrite('./demo_image/'+str(line_n)+'_'+str(counts)+'_'+str(wordsize)+'.png',box[0])
        count += 1


def delete_img(src): ## src : folder path
    for image_file in os.listdir(src):
      if image_file.endswith(".png"):
        os.remove(src+ image_file)
    ## demo image 없애기
    
def get_text(txt_list): ## txt_list : 단어 저장소, path : to write there
    line = 1
    get_text = ""
    for i in txt_list:
        if line == int(i[1]):
            get_text += i[0]
        else:
            get_text +='\n'
            get_text += i[0]
            line +=1
    line = 1
    print(get_text)
    #get_text = ""
    