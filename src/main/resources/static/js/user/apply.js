



function apply() {
    var name = document.getElementById("name").value;
    var info = document.getElementById("info").value;
    var peoplename = document.getElementById("people_name").value;
    var peoplenum = document.getElementById("people_num").value;
    if(name == null){
        layer.msg("请输入店铺名称");
    }
    if(info == null){
        layer.msg("请输入店铺介绍");
    }
    if(peoplename == null){
        layer.msg("请输入真实姓名");
    }
    if(people_num == null){
        layer.msg("请输入身份证号");
    }
    var params={};
    params.name = name;
    params.info = info;
    params.peopleName = peoplename;
    params.peopleNum = peoplenum;
    $.ajax({
        url:"/user/apply_shop",
        type:"POST",
        data: params,
        success:function(data){
            if(data.code == 0){
                layer.msg("提交成功！");
                location.href="update_id.html";
            }else{
                layer.msg(data.msg);
            }
        },
        error:function(){
            layer.msg("客户端请求有误");
        }
    });
}
