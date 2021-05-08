$(function(){
    getDetail();
});

function getDetail(){
    $.ajax({
        url:"/user/getInfo",
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
    $("#content").html("");
    $("#name").html("");
        var user = data;
        var role;
        if(user.authority ==1){
            role = "用户";
        }
        if (user.authority > 1){
            role = "商家";
        }
        if (user.authority == 0){
            role = "管理员";
        }
    var tr = $("<fieldset class=\"layui-elem-field layui-field-title\" style=\"margin-top: 20px;\">"+
        "<legend>基本资料</legend>"+" </fieldset>"+
        "<div class=\"col-sm-6 col-md-3\">\n" +
            "<div class=\"layui-card\">"+
            "<div class=\"layui-card-header\">个人信息（注意：在这里你可以修改自己的信息）<br></div>"+
            "<div class=\"layui-card-body\" style=\"\n" +
        "padding-left: 40%;>"+
        "<ul >"+
        "<li >"+
            "<p style=\" text-align:left;font-size: 20px;left: 50%\">"+"用户电话："+user.phonenumber+"</p>"+"<br>"+
        "</li>"+
        "<li >"+
        "<p style=\" text-align:left;font-size: 20px;left: 50%\">"+"用户头像："+ "<img style='vertical-align: top;height:118px;width:118px' class=\"layui-upload-img\" id=\"head\">"+"</p>"+"<br>"+
        "<div class=\"layui-upload\">"+"修改头像功能："+
        "<button type=\"button\" class=\"layui-btn layui-btn-normal\" id=\"test8\">选择文件</button>"+
        "<button type=\"button\" class=\"layui-btn\" id=\"test9\">开始上传</button>"+
        "</div>"+
        "</li>"+

        "<li >"+
             " <p style=\"text-align:left;font-size: 20px\">"+"用户称昵："+user.nickname+"</p>"+"<br>"+
        "</li>"+
        "<li >"+
             " <p style=\"text-align:left;font-size: 20px\">"+"用户登录次数："+user.logincount+"</p>"+"<br>"+
        "</li>"+
        "<li style=\"text-align:left\">"+
            " <p style=\"text-align:left;font-size: 20px\">"+"用户上次登录时间："+user.lastLoginDate+"</p>"+"<br>"+
        "</li>"+
        "<li style=\"text-align:left\">"+
        " <p style=\"text-align:left;font-size: 20px\">"+"用户注册时间："+user.registerdate+"</p>"+"<br>"+
        "</li>"+
        "<li style=\"text-align:left\">"+
        " <p style=\"text-align:left;font-size: 20px\">"+"用户目前权限："+role+"</p>"+"</li>"+"</ul>"+"</div>" +"</div>"+"</div>"

        );
        $("#content").append(tr);
        $("#name").append(user.nickname);
        document.getElementById("MyHead").src=user.head;
        document.getElementById("head").src=user.head;
}

function getChangePassword() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/change_pass.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function getAddress() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/address.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function getOrder() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/order.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}
function getCart() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/shoping_cart.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function applyShop() {
    $.ajax({
        url:"/user/status",
        type:"GET",
        success:function(data){
            if(data.data.code == 108){
                $("#content").html("");
                $("#content").html("<iframe src=\"/seller_index.html\" scrolling=\"yes\" width=\"100%\" height=\"100%\"> </iframe>");
                // location.href = "insert_goods.html";
            }else if (data.data.code == 109){
                $("#content").html("");
                $("#content").html("<iframe src=\"/baned.html\" scrolling=\"yes\" width=\"100%\" height=\"100%\"> </iframe>");
                // location.href ="baned.html";
            }else if (data.data.code == 110){
                $("#content").html("");
                $("#content").html("<iframe src=\"/audit_shop.html\" scrolling=\"yes\" width=\"100%\" height=\"100%\"> </iframe>");
            }else if (data.data.code == 111){
                $("#content").html("");
                $("#content").html("<iframe src=\"/apply_shop.html\" scrolling=\"yes\" width=\"100%\" height=\"100%\"> </iframe>");
                // location.href ="apply_shop.html";
            }
            else{
                layer.msg(data.data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });

}