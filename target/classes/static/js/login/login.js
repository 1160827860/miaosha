$(
    function () {
    var tab = 'account_number';
    $('#num').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#pass').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#veri').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#num2').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    $('#veri-code').keyup(function (event) {
        $('.tel-warn').addClass('hide');
        checkBtn();
    });

    // 按钮是否可点击
    function checkBtn() {
        $(".log-btn").off('click');
        var inp = $.trim($('#num').val());
        var pass = $.trim($('#pass').val());
        if (inp != '' && pass != '') {
            if (!$('.code').hasClass('hide')) {
                code = $.trim($('#veri').val());
                if (code == '') {
                    $(".log-btn").addClass("off");
                } else {
                    $(".log-btn").removeClass("off");
                    sendBtn();
                }
            } else {
                $(".log-btn").removeClass("off");
                sendBtn();
            }
        } else {
            $(".log-btn").addClass("off");
        }
    }

    function checkAccount(username) {
        if (username == '') {
            $('.num-err').removeClass('hide').find("em").text('请输入账户');
            return false;
        } else {
            $('.num-err').addClass('hide');
            return true;
        }
    }

    function checkPass(pass) {
        if (pass == '') {
            $('.pass-err').removeClass('hide').text('请输入密码');
            return false;
        } else {
            $('.pass-err').addClass('hide');
            return true;
        }
    }

    function checkCode(code) {
        if (code == '') {
            return false;
        } else {
            return true;
        }
    }


    function checkPhoneCode(pCode) {
        if (pCode == '') {
            $('.error').removeClass('hide').text('请输入验证码');
            return false;
        } else {
            $('.error').addClass('hide');
            return true;
        }
    }

    // 登录点击事件
    function sendBtn() {
        $(".log-btn").click(function () {
            // var type = 'phone';
            var inp = $.trim($('#num').val());
            var pass = $.trim($('#pass').val());
            var salt = g_passsword_salt;
            var str = ""+salt.charAt(0)+salt.charAt(2) + pass +salt.charAt(5) + salt.charAt(4);
            var formPass = md5(str);
            var params = {};
            params.phone_number = inp;
            params.passWords = formPass;
            if (checkAccount(inp) && checkPass(pass)) {
                if (!$('.code').hasClass('hide')) {
                    code = $.trim($('#veri').val());
                    //检查验证码
                    if (!checkCode(code)) {
                        return false;
                    }
                    params.checkCode = code;
                }

                $.ajax({
                    type: "POST",
                    url: "/login/do_login",
                    data: params,
                    success: function (r) {
                        if (r.code == 0) {
                                parent.location.href = '/';
                        }else if (r.code == 120){
                            parent.location.href = '/root/to_root';
                        }
                        else {
                            layer.msg(r.msg);
                        }
                    }
                });
            } else {
                return false;
            }
        });
    }

    // 登录的回车事件
    $(window).keydown(function (event) {
        if (event.keyCode == 13) {
            $('.log-btn').trigger('click');
        }
    });


    var refreshCode = function () {
        $.ajax({
            url: '/login/getCheckCode',
            type: 'GET',
            async: true,
            data: {},
            success: function (data) {
                $("#code_img").attr('src', data);
            },
            error: function () {
                status = false;
                // return false;
            }
        });
    }
    $("#code_img").on('click', function () {
        refreshCode();
    })

    refreshCode();

        
});