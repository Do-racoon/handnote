from flask_restful import Resource, reqparse, Api
from flask import Flask, make_response
from json import dumps

app = Flask(__name__)
api = Api(app)

class ImageToString(Resource):
    def get(self):
        try:
        	f = request.files['file']


            # parser = reqparse.RequestParser()
            
            # parser.add_argument('x', required=True, type=int, help='x cannot be blank')
            # parser.add_argument('y', required=True, type=int, help='y cannot be blank')
            # args = parser.parse_args()
            # result = args['x'] + args['y']
            return {'imgName': 'www', 'text': "What??"}
        except Exception as e:
            return {'error': str(e)}

api.add_resource(ImageToString, '/img-string')

if __name__ == '__main__':
        app.run(host='0.0.0.0', port=5000, debug=True)
