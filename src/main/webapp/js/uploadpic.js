$(function () {
    $("#fileupload").change(function(){ //ѡ���ļ� 
        $("#myupload").ajaxSubmit({ 
            dataType:  'text', //���ݸ�ʽΪjson 
            beforeSend: function() { //��ʼ�ϴ� 
            }, 
            uploadProgress: function(event, position, total, percentComplete) { 
            }, 
            success: function(data) { //�ɹ� 
               $("#picurl").val(data);
               $("#picurllink").attr("href", data); 
            }, 
            error:function(xhr){ //�ϴ�ʧ�� 
            } 
        }); 
    }); 
}); 