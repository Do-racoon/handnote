var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#delete').on('click', function () {
            _this.delete();
        });
        $('#btn-convert').on('click', function () {
            _this.convert(0);
        });
        $('#btn-hl-convert').on('click', function(){
            _this.convert(1);
        })
        $('#getContent').on('click', function(){
            _this.content();
        });
    },
    // 새로 작성한 글 저
    save : function () {
        var data = {
            "title": $('#inputTitle').val(),
            "text": $('#text').html()
        };

        $.ajax({
            type: 'POST',
            url: '/content',
            dataType: 'text',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('새 글이 저장되었습니다.');
            // 저장 후 이미지 삭제
            deleteImageAction(sel_index);
            // 썸네일 삭제
           $("#imageContainer > img").remove();
           // 텍스트 비우기
           $("#text").empty();
           $("#inputTitle").val("");
            //window.location.href = '/dashboard';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    // 사진을 글씨로 환 해주는 기능
    convert : function (highlight) {
//            var form = $("#form-convert")[0];
//            var form = new FormData($("#form-convert")[0]);
            var fileList = $("#convert")[0].files;


             for(var i=0, len=fileList.length; i<len; i++) {
                var data = new FormData();
                   //sel_files[i]
                data.append("mFile", fileList[i]);
                data.append("index", i)
                data.append("highlight", highlight);

                // 파일 이름 저장
                titleList.push(fileList[i].name.split(".", 1));
                textList.push("");

                //data.append("image_count", 1);
                let obj = document.getElementById("Img_id_"+i);
                obj.style.border = "3px solid red";

                $.ajax({
                    type: 'POST',
                    url: '/fileupload',
                    enctype: 'multipart/form-data',
                    processData : false,
                    contentType: false,
                    data: data
                }).done(function(str) {
                    //alert('글로 변환되었습니다.');
                    //$("#text").html(str);
                    // 인덱스와 text로 나누기 위한 것
                    str_split = str.split("@",2);
                    // 첫번째 인자가 인덱스
                    var idx = parseInt(str_split[0]);
                    // 두번째 인자가 text
                    str = str_split[1];

                    textList[idx] = str;

                    // 텍스트 받은 것을 체크하기 위한 것
                    let obj = document.getElementById("Img_id_"+idx);
                    obj.style.border = "3px solid blue";

                    after_convert = 1;
                }).fail(function (error) {
                    alert(JSON.stringify(error));
                });

            }



    },
    // 기존의 작성한 글을 가져오는 기능
    content : function(){
        $.ajax({
            type: 'GET',
            url: '/content/'+id,
            dataType: 'text',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
        //
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();