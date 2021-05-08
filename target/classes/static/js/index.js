$(function(){
    checkLogin();
    showUser();
    getGoods();
    getMiaoshaGoods();
});

function getMiaoshaGoods() {
    $.ajax({
        url:"/miaosha/to_list",
        type:"GET",
        success:function(data){
            if(data.code == 0){
                renderMiaosha(data.data);
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}
function renderMiaosha(data) {
    for(var i = 0;i < data.length;i++){
        var good = data[i];
        var tr = $("<div class=\"col-sm-6 col-md-3\">\n" +
            "    <div class=\"thumbnail\">"+"<img  style=\"\n" +
            "    width: 180px;\n" +
            "    height: 180px;\n" +
            "\" src="+good.goodsImg+"\n" +
            "alt=\"商品图片\">"+  "<div class=\"caption\">"+
            "<h3>"+good.goodsName+"</h3>"+
            " <p>"+good.goodsTitle+"</p>"+
            " <p>"+good.shopName+"</p>"+
            " <p>"+"商品原价："+good.goodsPrice+"</p>"+
            " <p>"+"秒杀价格："+good.miaoshaPrice+"</p>"+
            "<p>"+
            " <a href=\"/goods_detail.html?goodsId="+good.id +"\""+ "class=\"btn btn-primary\" role=\"button\">"+
            "商品详情"+"</a>" +"</p>" +"</div>" +"</div>" +"</div>");
        $("#content").append(tr);
    }
}
function getGoods() {
    $.ajax({
        url:"/goods/getAllGoods",
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

function apple() {
    $.ajax({
        url:"/user/apple",
        type:"POST",
        success:function(data){
            if(data.code == 0){
                layer.msg("您发送的")
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}

function checkLogin() {
    $.ajax({
        url:"/user/check_login",
        type:"GET",
        async: false,
        success:function(data){
            if(data.code == 0){
                getDetail();
            }else{
                layer.msg("用户登录已经过期或未登录");
                window.location.href = "login.html";
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
            "    <div class=\"thumbnail\">"+"<img  style=\"\n" +
            "    width: 180px;\n" +
            "    height: 180px;\n" +
            "\" src="+good.goodsImg+"\n" +
            "alt=\"商品图片\">"+  "<div class=\"caption\">"+
            "<h3>"+good.goodsName+"</h3>"+
            " <p>"+good.goodsTitle+"</p>"+
            " <p>"+good.shopName+"</p>"+
            " <p>"+"商品价格："+good.goodsPrice+"</p>"+
            "<p>"+
            " <a href=\"/goods.html?goodsId="+good.id +"\""+ "class=\"btn btn-primary\" role=\"button\">"+
             "商品详情"+"</a>" +"</p>" +"</div>" +"</div>" +"</div>");
        $("#content").append(tr);
    }
    
}
function showUser() {
    $.ajax({
        url:"/user/getInfo",
        type:"GET",
        success:function(data){
            if(data.code == 0){
                show(data.data);
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}
function show(data) {
    var user = data;
    $("#name").append(user.nickname);
    document.getElementById("MyHead").src=user.head;
}
