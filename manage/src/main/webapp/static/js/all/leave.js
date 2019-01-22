layui.use(['form', 'layer','laydate'], function () {
    // 操作对象
    var form = layui.form
        , layer = layui.layer
        ,laydate = layui.laydate
        , $ = layui.jquery;

    var option1;
    var id1;
    var leaveFrom1;
    var leaveTo1;
    var interval;
    
    function getReceiveUser() {
        var option = '';
        $.post({
            url : "/user/receiveUser",
            contentType : "application/json",
            dataType : "json",
            success : function(data) {
                if(data.success){
                    option += "<option value=''>----请选择----</option>";
                    for(var i=0;i<data.data.length;i++){
                        option +="<option value=\""+data.data[i].id+"\">"+data.data[i].userName+"-"+data.data[i].realName+"</option>"; //动态添加数据
                    }
                    option1 = option;
                    $("select[name=receive-user]").empty();
                    $("select[name=receive-user]").append(option1);
                    form.render('select');
                }else{
                    $(".layui-card-body").hide();
                }
            },
            error : function(xmlq, errq) {
                layer.open({
                    title: 'ERROR'
                    ,content: errq
                    ,closeBtn: 0
                    ,btn: "取消"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        });
    }

    $(function () {
        getReceiveUser();
        $("#showError").html();

        //循环执行，每隔1秒钟执行一次 1000
        interval=window.setInterval(refreshCount, 30000);
        //去掉定时器的方法
        // window.clearInterval(interval);
    })

    function refreshCount() {
        window.location.reload();
    }

    $('#send-message').on('click',function () {
        $("#showError").html();
        var userId = $("#user-id").val();
        var content = $("#content").val();
        content = content.replace(/^\s+|\s+$/gm,'');
        var receiveUser = $("#receive-user").val();
        if(receiveUser == null || receiveUser == ''){
            $("#showError").html("接收人不能为空");
            return;
        }
        if(content == null || content == ''){
            $("#showError").html("消息内容不能为空");
            return;
        }
        var param = {"leaveFrom":userId,"content":content,"leaveTo":receiveUser};
        $.ajax({
            type : "post",
            url : "/leave/sendLeave",
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify(param),
            success : function(data) {
                if (data.success) {
                    layer.msg('发送成功', {
                        offset: '20px'
                        ,icon: 1
                        ,time: 1000
                    }, function(){
                        window.location.reload();
                    });
                } else {
                    layer.open({
                        title: 'ERROR'
                        ,content: data.msg
                        ,closeBtn: 0
                        ,btn: "重新发送"
                        ,skin: 'layui-layer-molv'
                        ,icon :2
                        ,yes: function (index, layero) {
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
                    ,btn: "重新发送"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        })
    });

    // $('.back-message').on('click',function () {
    //
    // });

    window.backMessage = function(id,leaveFrom,leaveTo) {
        id1 = id;
        leaveFrom1 = leaveFrom;
        leaveTo1 = leaveTo;
        layer.open({
            title: '回复'
            ,type:1
            ,moveOut:true
            ,area:["700px","200px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-receive-message")
        });
    }
    
    window.showBackMessage = function (id) {
        var param = {"id":id};
        $.ajax({
            type : "post",
            url : "/leave/selectReceiveContent",
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify(param),
            success : function(data) {
                if (data.success) {
                    var id2 = data.data.parentId;
                    $("#"+id2).html(data.data.content);
                } else {
                    layer.open({
                        title: 'ERROR'
                        ,content: data.msg
                        ,closeBtn: 0
                        ,btn: "退出"
                        ,skin: 'layui-layer-molv'
                        ,icon :2
                        ,yes: function (index, layero) {
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
                    ,btn: "退出"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        });
    }

    window.showReceiveMessage = function (id) {
        var param = {"id":id};
        $.ajax({
            type : "post",
            url : "/leave/selectReceiveContent",
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify(param),
            success : function(data) {
                if (data.success) {
                    var id2 = data.data.parentId;
                    $("#"+id2).html(data.data.content);
                } else {
                    layer.open({
                        title: 'ERROR'
                        ,content: data.msg
                        ,closeBtn: 0
                        ,btn: "退出"
                        ,skin: 'layui-layer-molv'
                        ,icon :2
                        ,yes: function (index, layero) {
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
                    ,btn: "退出"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        });
    }

    $('#receive-message').on('click',function () {
        $("#showError1").html();
        var content = $("#content1").val();
        content = content.replace(/^\s+|\s+$/gm,'');
        if(content == null || content == ''){
            $("#showError1").html("消息内容不能为空");
            return;
        }
        var param = {"leaveFrom":leaveTo1,"content":content,"leaveTo":leaveFrom1,"parentId":id1};
        $.ajax({
            type : "post",
            url : "/leave/sendLeave",
            dataType : "json",
            contentType : "application/json",
            data : JSON.stringify(param),
            success : function(data) {
                if (data.success) {
                    layer.msg('发送成功', {
                        offset: '20px'
                        ,icon: 1
                        ,time: 1000
                    }, function(){
                        window.location.reload();
                    });
                } else {
                    layer.open({
                        title: 'ERROR'
                        ,content: data.msg
                        ,closeBtn: 0
                        ,btn: "重新发送"
                        ,skin: 'layui-layer-molv'
                        ,icon :2
                        ,yes: function (index, layero) {
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
                    ,btn: "重新发送"
                    ,skin: 'layui-layer-molv'
                    ,icon :2
                    ,yes: function (index, layero) {
                        layer.close(index);
                    }
                });
            }
        })
    });
});