function changePass() {
    var oldpass = document.getElementById("oldpas").value;
    var newpass = document.getElementById("newpas").value;
    var checkpass = document.getElementById("checkpass").value;
    if(oldpass == null){
        layer.msg("请输入原来密码");
    }
    if(newpass == null){
        layer.msg("请输入新密码");
    }
    if(checkpass == null){
        layer.msg("请再次输入密码");
    }
    if(newpass == oldpass ){
        layer.msg("输入新密码和原来密码一样");
    }else if(checkpass != newpass){
        layer.msg("两次密码输入不一样");
    }else {
        var params={};
        params.oldPasswords = oldpass;
        params.newPasswords = newpass;
        params.checkPassWords = checkpass;
       doChangePasswords(params);
    }
}
function doChangePasswords(params) {
    $.ajax({
        url:"/user/change_password",
        type:"POST",
        data: params,
        success:function(data){
            if(data.code == 0){
                layer.msg("密码修改成功！");
                top.location.href="login.html";
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}