function addAddress() {
    var name = document.getElementById("name").value;
    var phone = document.getElementById("phone").value;
    var address = document.getElementById("address").value;
    if(name == null){
        layer.msg("请输入收件人姓名");
    }
    if(phone == null){
        layer.msg("请输入收件人电话");
    }
    if(address == null){
        layer.msg("请输入地址");
    }
    var params={};
    params.name = name;
    params.address = address;
    params.phone = phone;
    $.ajax({
        url:"/user/add_address",
        type:"POST",
        data: params,
        success:function(data){
            if(data.code == 0){
                layer.msg("添加成功！");
                location.href="address.html";
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}
function getAddAsPage() {
    location.href = "add_address.html";
}
