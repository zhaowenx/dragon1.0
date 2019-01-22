layui.config({
    base: '../plugins/layui/' //静态资源所在路径
}).use(['form','layer'], function(){
    var $ = layui.$
        ,form = layui.form
        ,layer = layui.layer;

    // 验证
    form.verify({
        password: function (value) {
            if (value == "") {
                return "请输入密码";
            }
            if(value.length != 6){
                return "密码必须是6位!";
            }
        }
    });

    //默认对全部类型的表单进行一次更新
    form.render();

    $(".tip_txt").hide();
    $("#forgetPassword").hover(function () {
        $(".tip_txt", this).show();
    }, function () {
        $(".tip_txt", this).hide();
    });

    //密码输入框、验证码输入框回车
    $("#LAY-user-login-username,#LAY-user-login-password,#LAY-user-login-vercode").keydown(function (e) {
        e.stopPropagation();
        if (e.keyCode == 13) {
            login();
        }
    });

    //提交
    form.on('submit(LAY-user-login-submit)', function(obj){
        login();
    });
});

function login() {
    var userName = $("#LAY-user-login-username").val();
    var passWord = $("#LAY-user-login-password").val();
    var vercode = $("#LAY-user-login-vercode").val();
    var user = {"userName":userName,"passWord":passWord,"validateCode":vercode}
    $.ajax({
        type : "post",
        url : "/login/getSecret",
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(user),
        success : function(data) {
            if (data.success) {
                var uuid = data.data;
                var desUuid = DES3.encrypt(uuid, passWord);
                var userDTO =  {"userName" : userName, "passWord" : desUuid};
                $.ajax({
                    type : "post",
                    url : "/login/toCheck",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON.stringify(userDTO),
                    success : function(data) {
                        if (data.success) {
                            //登入成功的提示与跳转
                            layer.msg('登入成功', {
                                offset: '20px'
                                ,icon: 1
                                ,time: 1000
                            }, function(){
                                changeImg();
                                window.location.href="/login/toIndex";
                            });
                        } else {
                            layer.open({
                                title: 'ERROR'
                                ,content: data.msg
                                ,closeBtn: 0
                                ,btn: "重新登录"
                                ,skin: 'layui-layer-molv'
                                ,icon :2
                                ,yes: function (index, layero) {
                                    changeImg();
                                    layer.close(index);
                                }
                            });
                        }
                    },
                    error : function(xmlq, errq) {
                        layer.open({
                            title: 'ERROR'
                            ,content: errq
                            ,closeBtn: 0
                            ,btn: "重新登录"
                            ,skin: 'layui-layer-molv'
                            ,icon :2
                            ,yes: function (index, layero) {
                                changeImg();
                                layer.close(index);
                            }
                        });
                    }
                });
            } else {
                layer.open({
                    title: 'ERROR'
                    ,content: data.msg
                    ,closeBtn: 0
                    ,btn: "重新登录"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        changeImg();
                        layer.close(index);
                    }
                });
            }
        },
        error : function(xmlq, errq) {
            layer.open({
                title: 'ERROR'
                ,content: errq
                ,closeBtn: 0
                ,btn: "重新登录"
                ,skin: 'layui-layer-molv'
                ,icon :2
                ,yes: function (index, layero) {
                    changeImg();
                    layer.close(index);
                }
            });
        }
    });
}

function changeImg() {
    //时间戳
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳
    var timestamp = (new Date()).valueOf();
    newUrl = "/login/getValidateCode" + "?timestamp=" + timestamp;
    $("#LAY-user-get-vercode").attr("src",newUrl);
}