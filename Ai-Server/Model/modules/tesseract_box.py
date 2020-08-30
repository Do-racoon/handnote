import cv2
import copy
import pytesseract
import os
from pytesseract import Output
from collections import defaultdict

import numpy as np

# tessercat box 치는 작업
def tesseract_box(img, highlight): 
	## path : img path
	img_cl = copy.deepcopy(img)

	if highlight:
	    img_cp = copy.copy(img)
	    img_hsv = cv2.cvtColor(img_cl, cv2.COLOR_BGR2HSV)
	    img_yellow = cv2.inRange(img_hsv, np.array([25,120,120]),np.array([32,255,255]))
	    img_pink = cv2.inRange(img_hsv, np.array([144,50,50]), np.array([184, 235, 255]))
	    img_green = cv2.inRange(img_hsv,  np.array([40,50,50]),np.array([79, 255, 252]))

	    for mask_ in [img_yellow, img_pink, img_green]:
	    	img_result = cv2.bitwise_not(img_cp, img_cp, mask = mask_)
	    	
	    img_result_white = cv2.bitwise_or(img_result, img_cl)
	    img_cl = img_result_white

	d = pytesseract.image_to_data(img_cl,output_type=Output.DICT)

	value_dict = list(d.values())

	# 인덱스 저장할 딕셔너리
	index_dict = defaultdict(int)

	# bottom, left, top
	bottom = [ value_dict[7][i] + value_dict[9][i] for i in range(len(value_dict[7])) ]
	left = value_dict[6]
	top = value_dict[7]
	width = value_dict[8]

	  
	# (left, top, bottom) 만들기 위한 작업
	for idx, value in enumerate(zip(left, top, bottom)):
	  if int(value_dict[10][idx]) > -1:
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

	  # 다른 라인일 때,
	  else:
	    temp.append((order[idx-1], line_num))
	    order_line.append(temp)
	    line_num+=1
	    temp = []

	  if len(order)-1 == idx:
	    temp.append((order[idx], line_num))
	    order_line.append(temp)
	    break

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
	    space = 10
	    if val != 0 and left - merge_data[-1][1] < space and merge_data[-1][4] == line: ## 라인이 같고, 띄어쓰기가 올바르지 않을경우 합치기
	        t = merge_data[-1]
	        merge_data[-1] = (t[0], right, min(t[2], top), max(t[3], bottom),merge_data[-1][4],d['height'][i])
	    else: ## 다를 경우 merge_data에 저장
	        merge_data.append((left, right, top, bottom,line,d['height'][i]))
	        

	    val += 1

	for t in merge_data: ## 네모 친 이미지를 box_list에 추가
	    box_list.append((img_cl[t[2]:t[3],t[0]:t[1]], t[4],t[5]))
	return box_list

def highlight_box_list(box_list): ##highlighting 기능
    count =0
    box_highlight_list = []
    rgb_info = defaultdict(int)
    rgb_info_ori = defaultdict(int)
    for box in box_list:
        for rgb in box[0]:
          for r in rgb:
            rgb_info_ori[tuple(r)] += 1
            if r[0]+ r[1]+ r[2] >= min([r[0],r[1],r[2]])*3 + 20:
              rgb_info[tuple(r)] += 1
              count = 1
        if count == 1:
          box_highlight_list.append((box[0],box[1],box[2]))
          count = 0
    return box_highlight_list