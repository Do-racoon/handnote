var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#delete').on('click', function () {
            _this.delete();
        });
<<<<<<< HEAD
        $('#btn-convert').on('click', function () {
            _this.convert();
        });
<<<<<<< HEAD
=======
>>>>>>> 78460726eb6d9844439e0eba97cc744eede08c18
=======
        $('#getContent').on('click', function(){
            _this.content();
        });
>>>>>>> d700ae6... design all done.
    },
    // 새로 작성한 글 저
    save : function () {
        var data = {
            "title": $('#inputTitle').val(),
            "text": $('#text').html()
        };
        console.log(data['text']);
        $.ajax({
            type: 'POST',
            url: '/content',
            dataType: 'text',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('새 글이 저장되었습니다.');
            window.location.href = '/dashboard';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
<<<<<<< HEAD
    update : function () {
        var data = {
            title: $('#inputTitle').val(),
            text: $('#text').html()
        };

        $.ajax({
            type: 'PUT',
            url: '/content/'+id,
            dataType: 'text',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 저장되었습니다.');
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
<<<<<<< HEAD
=======
    // 사진을 글씨로 환 해주는 기능
>>>>>>> d700ae6... design all done.
    convert : function () {
            var form = $("#form-convert")[0];
            var formData = new FormData(form);

            $.ajax({
                type: 'POST',
                url: '/fileupload',
                enctype: 'multipart/form-data',
                processData : false,
                contentType:false,
                data: formData
            }).done(function(str) {
                alert('글로 변환되었습니다.');
                var convertText = JSON.stringify(str);
                console.log(convertText);
                $("#text").text(convertText);
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
    },
<<<<<<< HEAD
=======
>>>>>>> 78460726eb6d9844439e0eba97cc744eede08c18
    delete : function () {
=======
    // 기존의 작성한 글을 가져오는 기능
    content : function(){
>>>>>>> d700ae6... design all done.
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