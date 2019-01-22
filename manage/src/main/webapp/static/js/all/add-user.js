layui.use(['form'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.$;

    //提交
    form.on('submit(addUser)', function(data){
        $.post('/user/addUser',data.field,function (res) {
            if(res.success){
                layer.msg('新增用户成功,跳转到登录页面', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    parent.location.href="/login/logout";
                    // window.location.reload();
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
                        $("#submit").show();
                        layer.close(index);
                    }
                });
            }
        });
        return false;
    });
});