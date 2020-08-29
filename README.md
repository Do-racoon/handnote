# Handnote

수기로 작성한 문서가 많아지게 되면 가지고 다니기 불편할 뿐만 아니라, 관리하기도 불편하다. 그래서 웹에 사진을 찍어 업로드하면 텍스트로 변환하여 편집할 수 있고, 손쉽게 관리하면 좋을 것 같아 개발하게 되었다.

---------------------------------

## 기능

- 정리한 글의 이미지 → 텍스트

- 하이라이트된 글씨 → 텍스트

- 에디터

- 저장

-----------------------------------

## Architecture

![handnote_architecture](https://user-images.githubusercontent.com/42924998/91634987-aa50b580-ea2f-11ea-86f0-9bbc8688d503.png)

-----------------------------------

## 구현 화면

![시작화면](https://user-images.githubusercontent.com/42924998/91634920-44642e00-ea2f-11ea-8c51-40cdce790b28.png)  


![로그인 후](https://user-images.githubusercontent.com/42924998/91634955-7d9c9e00-ea2f-11ea-9c49-f127f43786db.png)

▶  New Edit 버튼을 클릭하여 새로운 노트를 작성 ( 텍스트로 바꿀 이미지 필수)  



![new edit](https://user-images.githubusercontent.com/42924998/91634958-81302500-ea2f-11ea-899a-39c4e53210a8.png)
▶  파입 업로드로 인식할 사진을 선택하고, **[텍스트로 변환]** 혹은 **[중요부분 변환]** 버튼을 누르면 인식한 글씨가 텍스트창에 들어가게 됌.  

**[텍스트로 변환]** - 이미지의 모든 글씨 인식

**[중요부분 변환]** - 이미지의 하이라이트한 글씨만 인식  
  
Link: http://3.34.203.63:8080/

--------------------------------------

## 도커

`docker-compose up`

--------------------------------------

## Contact Us

정현수 <mses1572@naver.com>

이지헌 <dlwlgjs132@naver.com>

임도연 <dodocat52@naver.com>
