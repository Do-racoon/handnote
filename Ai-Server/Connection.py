from flask_restful import Resource, Api, reqparse
from flask import Flask
import json
import cv2
import werkzeug
import numpy as np
from Model import demo

app = Flask(__name__)
api = Api(app)

class ImageToString(Resource):
    def post(self):
        parser = reqparse.RequestParser()

        # 스프링 - id, img 정보
        # werkzeug.datastructures.FileStorage - 파일
        # parser.add_argument('id', required=True, type=int)
        parser.add_argument('img', required=True, type=werkzeug.datastructures.FileStorage, location='files')
        parser.add_argument('highlight', required=True, type = int)

        args = parser.parse_args()

        # 서버로부터 받은 이미지 Stream => np => cv 처리
        img_stream = args['img'].stream.read()
        img_np = np.frombuffer(img_stream, np.uint8)    #fromstring => frombuffer
        img = cv2.imdecode(img_np,  cv2.IMREAD_COLOR)
 
 		# 하이라이트 기능 On/Off       
        img_info = demo.image_file(img, args['highlight'])
        
        send = []

        for info in img_info:
        	send.append({"text": info[0], "font": info[1], "line": info[2]})

        return send


api.add_resource(ImageToString, '/img-string')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
