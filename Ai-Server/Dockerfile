FROM python:3.7
MAINTAINER mses1572 <mses1572@naver.com>

WORKDIR /app
ADD . /app

RUN apt-get update
RUN apt-get install 'ffmpeg'\
    'libsm6'\ 
    'libxext6' -y
RUN pip3 install -r requirements.txt

CMD ["python3", "Connection.py"]
