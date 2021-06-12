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
    var user = data;
    $("#name").append(user.nickname);
    if (user.head!=null){
        document.getElementById("MyHead").src=user.head;
    }
}
function getUser() {
        $("#content").html("");
        $("#content").html("<iframe src=\"/root_user.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function getApply() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/root_apply.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function getGoods() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/root_goods.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

function getComplaint() {
    $("#content").html("");
    $("#content").html("<iframe src=\"/root_complaint.html\" scrolling=\"no\" width=\"100%\" height=\"100%\"> </iframe>");
}

