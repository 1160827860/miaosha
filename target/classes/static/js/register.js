$(function () {

    $("#registerId").on('click', function () {
        var phone = $("#phone").val().trim();
          if (phone == '') {
              layer.msg('请输入手机号', {icon: 5});
              return false;
          }
        var param = /^1[123456789]\d{9}$/;
        if (!param.test(phone) && $("#nation").val() == '86') {
            // globalTip({'msg':'手机号不合法，请重新输入','setTime':3});
            layer.msg('手机号不合法，请重新输入', {icon: 5});
            return false;
        }
        var password = $("#password").val().trim();
        if (password == '') {
            layer.msg('请输入密码', {icon: 5});
            return false;
        }
        param = /^[A-Za-z0-9]{6,18}$/;
        if (!param.test(password)) {
            layer.msg('密码不合法(数字字母,长度不能小于6位),请重新输入', {icon: 5});
            return false;
        }
        var cpassword = $("#cpass").val().trim();
        if (cpassword != password) {
            layer.msg('密码不一致', {icon: 5});
            return false;
        }

        var realName = $("#realName").val().trim();
        if (realName == '') {
            layer.msg('请输入称昵', {icon: 5});
            return false;
        }

        $.ajax({
            cache: true,
            type: "POST",
            url: "/user/register",
            data:{
                phone_number: phone,
                passWords: password,
                nickName: realName
            },
            async: false,
            processData:true,
            success: function (data) {
                if (data.code == 0) {
                     layer.confirm('提交成功，请耐心等待审核.', {icon: 1, btn: ['确定']}, function () {
                         window.location.href = '/login.html';
                     });
                    // layer.open({
                    //     yes: function (index, layero) {
                    //         parent.layer.close(index);
                    //         window.location.href = 'user/tologin';
                    //     }
                    // });
                    console.log(phone, password,realName);
                } else {
                    console.log(phone, password,realName);
                    layer.msg(data.msg, {icon: 5});
                }
            }
        });
    });
})