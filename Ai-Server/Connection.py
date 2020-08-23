from flask_restful import Resource, Api, reqparse
from flask import Flask, make_response
from json import dumps
import cv2
import werkzeug
import numpy as np


app = Flask(__name__)
api = Api(app)

class ImageToString(Resource):
    def post(self):
        parser = reqparse.RequestParser()

        # 스프링 - id, img 정보
        # werkzeug.datastructures.FileStorage - 파일
        # parser.add_argument('id', required=True, type=int)
        parser.add_argument('img_name', required=True, type=str)
        parser.add_argument('img', required=True, type=werkzeug.datastructures.FileStorage, location='files')
        args = parser.parse_args()

        # 서버로부터 받은 이미지 Stream => np => cv 처리
        img_stream = args['img'].stream.read()
        img_np = np.frombuffer(img_stream, np.uint8)    #fromstring => frombuffer
        img = cv2.imdecode(img_np,  cv2.IMREAD_COLOR)

        img_name = args['img_name']
		
        # Test용 - 저장
        cv2.imwrite(img_name,img)

        return "Hello"


api.add_resource(ImageToString, '/img-string')

if __name__ == '__main__':
        app.run(host='0.0.0.0', port=5000, debug=True)
