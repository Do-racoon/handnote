var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-convert').on('click', function () {
            _this.convert();
        });
    },
    save : function () {
        var data = {
            title: $('#inputTitle').val(),
            text: $('#text').html()
        };

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
    convert : function () {
            var form = $("#form-convert")[0];
            var formData = new FormData(form);

//            formData.append("file", $("#convert")[0].files[0]);

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
    delete : function () {
        $.ajax({
            type: 'DELETE',
            url: '/content/'+id,
            dataType: 'text',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('글이 삭제되었습니다.');
            window.location.href = '/dashboard';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();