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
            _this.convert();
        });
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
           console.log($("#text"))
           $("#inputTitle").val("");
            //window.location.href = '/dashboard';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    // 사진을 글씨로 환 해주는 기능
    convert : function () {
//            var form = $("#form-convert")[0];
//            var form = new FormData($("#form-convert")[0]);
            var fileList = $("#convert")[0].files;


             for(var i=0, len=fileList.length; i<len; i++) {
                var data = new FormData();
                   //sel_files[i]
                data.append("mFile", fileList[i]);

                // 파일 이름 저장
                titleList.push(fileList[i].name.split(".", 1));
                console.log(fileList[i].name.split(".", 1));
                //data.append("image_count", 1);

                console.log(fileList[i]);
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
                    console.log("ok");
                    textList.push(str);
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