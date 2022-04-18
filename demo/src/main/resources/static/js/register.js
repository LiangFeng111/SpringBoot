$(function (){
    var ifname = -1;
    var ifemail=-1
    var ifpassword=-1;
    var ur="http://localhost:8083/"
    $("#zhu").click(function () {
        var code = $("input[name='code']").val().trim();
        var errors = $("#errros");
        var emailName = $("input[name='emailName']").val();
        if (code !== ""){
            $.ajax({
                url: ur+"user/isEmail",
                data: {"code":code,"emailName":emailName},
                type: "post",
                success: function (data) {
                    var isCode = data["verify"];
                    if (isCode =="1"){
                        $("#email").css("border-color","#2c7")
                        console.log(isCode);
                        isUsername(false)
                        if (ifname===1){
                            isPassword();
                            if (ifpassword===1){
                                isEmail(false);
                            }
                        }
                        if (ifname!==-1 && ifemail!==-1 && ifpassword!==-1){
                            register();
                        }
                    }else {
                        $("#emailCode").css("border-color","#e33")
                        console.log(isCode)
                        errors.html("你的验证码不对哦，看清楚一点")
                        errors.removeAttr("hidden")
                    }

                },
                error: function (error) {
                    console.log(error)
                }
            })
        }else {
            $("#email").css("border-color","#e33")
            errors.html("你的验证码还没填呢")
            errors.removeAttr("hidden")
        }

    })



    //验证码
    function sendEmail(obj) {
        var email = $("input[name='emailName']").val();
        $.ajax({
            url:ur+"user/sendEmail",
            data:{"email":email},
            type:"post",
            async:obj,//为false为同步请求
            success:function (data){
            },
            error:function (error) {
                console.log(error)
            }
        })
    }


    function register() {
        var username = $("input[name='username']").val().trim();
        var password = $("input[name='password']").val().trim();
        var email = $("input[name='emailName']").val().trim();
        var userList = [];
        userList.push({"username": username, "password": password, "email": email})
        $.ajax({
            url: ur + "user/register",
            data: JSON.stringify(userList),
            type: "post",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                alert("注册成功，请登录！")
                console.log(data)
                window.location.href=ur+data;

            },
            error: function (error) {
                console.log(error)
            }
        })
    }

    //用户名判断
    function isUsername(obj) {
        var errors = $("#errros");
        var name =$("#username").val();
        var regName =/^\d[0-9A-Za-z]{5,11}$/;
        if (regName.test(name)){
            if (name.trim() !=="") {
                $.ajax({
                    url: ur+"user/findByName",
                    data: {"name": name},
                    type: "post",
                    async:obj,
                    success: function (data) {
                        $("#ic_user").css("color","#2c7")
                        $("#username").css("border-color","#2c7")
                        if ("1" == data) {
                            errors.prop("hidden", "hidden");
                            ifname=1;
                        } else {
                            ifname=-1;
                            errors.html("用户名太受欢迎了吧")
                            errors.removeAttr("hidden")
                            $("#ic_user").css("color","#e33")
                            $("#username").css("border-color","#e33")
                        }
                    },
                    error: function (error) {
                        console.log(error)
                    }
                })
            }
        }else {
            errors.html("用户名必须是字母或数字，最少为6位，最多12位")
            errors.removeAttr("hidden")
            $("#ic_user").css("color","#e33")
            $("#username").css("border-color","#e33")
        }
    }
    $("#username").blur(function () {
        isUsername();
    })
    //邮箱判断
    function isEmail(obj) {
        var errors = $("#errros");
        var email =$("input[name='emailName']").val();
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        if (!reg.test(email)){
            errors.html("您的邮箱格式不正确")
            errors.removeAttr("hidden")
            $("#ic_email").css("color","#e33")
            $("#emailName").css("border-color","#e33")
        }else {
            if (email.trim() !==""){
                $.ajax({
                    url: ur+"user/findByEmail",
                    data: {"email":email},
                    type: "post",
                    async:obj,
                    success: function (data) {
                        if ("1"==data){
                            errors.prop("hidden", "hidden");
                            ifemail=1;
                            $("#ic_email").css("color","#2c7")
                            $("#emailName").css("border-color","#2c7")
                        }else {
                            ifemail=-1;
                            errors.html("这个邮箱已经注册过了")
                            errors.removeAttr("hidden")
                            $("#ic_email").css("color","#e33")
                            $("#emailName").css("border-color","#e33")
                        }
                    },
                    error: function (error) {
                        console.log(error)
                    }
                })
            }
        }
    }
    $("input[name='emailName']").blur(function () {
        isEmail(true);
    })


    //密码判断
    function isPassword() {
        var password = $("input[name='password']").val();
        var errors = $("#errros");
        var regPassword = /^[a-zA-Z0-9]{8,11}$/;
        if (regPassword.test(password)){
            $("#password").css("border-color","#2c7")
            errors.prop("hidden", "hidden");
            ifpassword=1;
        }else {
            $("#password").css("border-color","#e33")
            errors.html("密码必须8位数,且不超过11位")
            errors.removeAttr("hidden")


        }
    }
    $("input[name='password']").blur(function () {

        isPassword();
    })


    //点击发送验证码
    $("input[name='email']").click(function () {
        var email = $("input[name='email']")
        settime(email)
        sendEmail(true);
    })

    //60秒之后获取验证码
    var countdown = 60;
    var sending = false;

    function settime(obj) { //发送验证码倒计时
        if (countdown === 0) {
            obj.attr('disabled', false);
            obj.val("发送验证码");
            countdown = 60;
            sending = false;
            return;
        } else {
            obj.attr('disabled', true);
            obj.val("重新发送(" + countdown + ")");
            countdown--;
        }
        setTimeout(function () {
            settime(obj);
        }, 1000)
    }
})
