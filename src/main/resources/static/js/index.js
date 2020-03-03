$(function(){
    getDetail();
});

function getDetail(){
    $.ajax({
        url:"/goods/to_list/",
        type:"GET",
        success:function(data){
            if(data.code == 0){
                render(data.data);
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}
function render(data) {
    for(var i = 0;i < data.length;i++){
        var good = data[i];
        var tr = $("<div class=\"col-sm-6 col-md-3\">\n" +
            "    <div class=\"thumbnail\">"+"<img src="+good.goodsImg+"\n" +
            "alt=\"商品图片\">"+  "<div class=\"caption\">"+
            "<h3>"+good.goodsName+"</h3>"+
            " <p>"+good.goodsTitle+"</p>"+"<p>"+
            " <a href=\"/goods_detail.html?goodsId="+good.id +"\""+ "class=\"btn btn-primary\" role=\"button\">"+
             "商品详情"+"</a>" +"</p>" +"</div>" +"</div>" +"</div>");
        $("#content").append(tr);
    }
}
