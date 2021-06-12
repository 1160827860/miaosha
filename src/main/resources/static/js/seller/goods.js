$(function(){

    $("#content").html("");
    $("#content").html("<iframe src=\"/insert_goods.html\" frameborder=\"0\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
});

function addGoods() {
    document.getElementById("add").className = "layui-nav-item layui-this";
    $("#content").html("");
    $("#content").html("<iframe src=\"/insert_goods.html\" frameborder=\"0\"scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function insertGoods() {
    var name = document.getElementById("name").value;
    var title = document.getElementById("title").value;
    var detail = document.getElementById("detail").value;
    var price = document.getElementById("price").value;
    var stock = document.getElementById("stock").value;
    if(name == null){
        layer.msg("请输入商品名称");
    }
    if(title == null){
        layer.msg("请输入商品标题");
    }
    if(detail == null){
        layer.msg("请输入商品描述");
    }
    if(price == null){
        layer.msg("请输入商品价格");
    }
    if(stock == null){
        layer.msg("请输入库存");
    }
    var params={};
    params.name = name;
    params.title = title;
    params.detail = detail;
    params.price = price;
    params.stock = stock;
    $.ajax({
        url:"/user/insert_goods",
        type:"POST",
        data: params,
        async:false,
        success:function(data){
            if(data.code == 0){
                layer.msg("提交成功！");
                location.href="insert_goods.html";
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}

function showGoods() {
    document.getElementById("show").className = "layui-nav-item layui-this";
    $("#content").html("");
    $("#content").html("<iframe src=\"/manage_map_data.html\" scrolling=\"yes\"frameborder=\"0\" width=\"100%\" height=\"100%\"> </iframe>");
}


function showOrder() {
    document.getElementById("order").className = "layui-nav-item layui-this";
    $("#content").html("");
    $("#content").html("<iframe src=\"/manage_order.html\" scrolling=\"yes\"frameborder=\"0\" width=\"100%\" height=\"100%\"> </iframe>");
}

function showMiaoSha() {
    document.getElementById("miaosha").className = "layui-nav-item layui-this";
    $("#content").html("");
    $("#content").html("<iframe src=\"/manage_miaosha.html\" scrolling=\"yes\"frameborder=\"0\" width=\"100%\" height=\"100%\"> </iframe>");
}

function showDel() {
    document.getElementById("del").className = "layui-nav-item layui-this";
    $("#content").html("");
    $("#content").html("<iframe src=\"/del_goods.html\" scrolling=\"yes\"frameborder=\"0\" width=\"100%\" height=\"100%\"> </iframe>");
}