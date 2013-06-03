$(function () {
    $("#fileupload").change(function(){ //选择文件 
        $("#myupload").ajaxSubmit({ 
            dataType:  'text', //数据格式为json 
            beforeSend: function() { //开始上传 
            }, 
            uploadProgress: function(event, position, total, percentComplete) { 
            }, 
            success: function(data) { //成功 
               $("#picurl").val(data);
               $("#picurllink").attr("href", data); 
            }, 
            error:function(xhr){ //上传失败 
            } 
        }); 
    }); 
}); 