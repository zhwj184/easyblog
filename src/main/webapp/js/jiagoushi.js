function autoScroll(obj){ 
    $(obj).find(".list").animate({ 
        marginTop : "-25px" 
    },500,function(){ 
        $(this).css({marginTop : "0px"}).find("li:first").appendTo(this); 
    }) 
} 
$(function(){ 
    setInterval('autoScroll(".scroll")',5000) 
}) 