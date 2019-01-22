layui.use(['form', 'layer'], function () {

    // 操作对象
    var form = layui.form
        , layer = layui.layer
        , $ = layui.jquery;

    // 验证
    form.verify({
        length: function (value) {
            if(value.length != 6){
                return "密码必须是6位!";
            }
        },
        check: function (value) {
            var newPassWord1 = $("#newPassWord1").val();
            var newPassWord2 = $("#newPassWord2").val();
            if(newPassWord1!=newPassWord2){
                return "两次输入的密码要相同";
            }
        }
    });

    // 提交监听
    form.on('submit(updatePassword)', function (data) {
        $.post('/user/updatePassWord',data.field,function (res) {
            if(res.success){
                layer.msg('修改密码成功，需要重新登录', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/logout";
                    // window.location.reload();
                    parent.location.href="/login/logout"
                });
            }else{
                layer.open({
                    title: 'ERROR'
                    ,content: res.msg
                    ,closeBtn: 0
                    ,btn: "确定"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        });
        return false;
    });
})