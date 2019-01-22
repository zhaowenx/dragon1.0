layui.use(['table', 'form', 'layer', 'vip_table','laydate'], function () {

    // 操作对象
    var form = layui.form
        , table = layui.table
        , layer = layui.layer
        , vipTable = layui.vip_table
        , laydate = layui.laydate
        , $ = layui.jquery;

    var option1;
    var option2;
    var option3

    //获取下拉框值的函数
    function getItemVal(dict) {
        var option = '';
        var param = {"dict":dict};
        $.post({
            url : "/dict/selectDictItemByDict",
            contentType : "application/json",
            dataType : "json",
            data : JSON.stringify(param),
            success : function(data) {
                option += "<option value=''>----请选择----</option>";
                for(var i=0;i<data.data.length;i++){
                    option +="<option value=\""+data.data[i].itemKey+"\">"+data.data[i].itemKey+"-"+data.data[i].itemVal+"</option>"; //动态添加数据
                }
                if("status" == dict){
                    option1 = option;
                }else if("stick" == dict){
                    option2 = option;
                }else if("messageType" == dict){
                    option3 = option;
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
        getItemVal("status");
        getItemVal("stick");
        getItemVal("messageType");
    });

    // //日期
    // laydate.render({
    //     elem: '#dailyDate'
    // });
    // laydate.render({
    //     elem: '#dailyDate1'
    // });
    // laydate.render({
    //     elem: '#dailyDate2'
    // });

    $("#close-btn-qx1").click(function () {
        $("#open-div-add-daily").hide();
    });
    $("#close-btn-qx2").click(function () {
        $("#open-div-update-daily").hide();
    });

    // 表格渲染
    var tableIns = table.render({
        elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            // {checkbox: true, fixed: true, space: true,width:80},
            {field: 'id', title: 'ID', width: 50,align: 'center'}
            , {field: 'notificationTitle', title: '通知标题', width: 100,align: 'center'}
            , {field: 'notificationContent', title: '内容', width: 380}
            , {field: 'publishName', title: '发布人', width: 100,align: 'center',templet:function (data) {
                return data.publishBy == 0?'--':data.publishName;
            }}
            , {field: 'publishDate', title: '发布时间', width: 170,align: 'center',templet:function (data) {
                return data.publishDate == null?'--':data.publishDate;
            }}
            , {field: 'createName', title: '创建人', width: 100,align: 'center'}
            , {field: 'createDate', title: '创建时间', width: 170,align: 'center'}
            , {field: 'updateName', title: '更新人', width: 100,align: 'center'}
            , {field: 'updateDate', title: '更新时间', width: 170,align: 'center'}
            , {field: 'status', title: '发布状态', width: 90,align: 'center',templet:function (data) {
                return data.status == 0?'发布':'未发布';
            }}
            , {field: 'stick', title: '是否置顶', width: 90,align: 'center',templet:function (data) {
                return data.stick == 1?'置顶':'未置顶';
            }}
            //消息类型：1，系统消息；2，其他消息
            , {field: 'messageType', title: '消息类型', width: 120,align: 'center',templet:function (data) {
                if(data.messageType == 1){
                    return "系统消息";
                }else if(data.messageType == 2){
                    return "生日消息";
                }else{
                    return "其他消息";
                }
            }}
            , {field: 'messageAttachment', title: '附件信息', width: 170,align: 'center',templet:function (data) {
                return data.messageAttachment == null||data.messageAttachment == ''?'--':data.messageAttachment;
            }}
            , {field: 'right1', title: '操作', width: 208, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
        ]]
        , id: 'dataCheck'
        , url: '/publish/show'
        , method: 'get'
        , page: true
        , limits: [10, 20, 30, 40, 50]
        , limit: 10
        , loading: true
        , request: {pageName: 'currentPage', limitName: 'pageSize'}
        , done: function (res, curr, count) {
            //如果是异步请求数据方式，res即为你接口返回的信息。
            //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
            // console.log(res);
        }
    });

    // 获取选中行
    // table.on('checkbox(dataCheck)', function (obj) {
    //     console.log(obj.checked); //当前是否选中状态
    //     console.log(obj.data); //选中行的相关数据
    //     console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
    // });

    table.on('tool(dateTable)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'delete'){ //删除
            layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
                var param = {"id":data.id};
                $.ajax({
                    type : "post",
                    url : "/publish/delete",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON.stringify(param),
                    success : function(data) {
                        if (data.success) {
                            layer.msg('删除成功', {
                                offset: '20px'
                                ,icon: 1
                                ,time: 1000
                            }, function(){
                                window.location.reload();
                                // parent.location.reload();
                            });
                        } else {
                            layer.open({
                                title: 'ERROR'
                                ,content: data.msg
                                ,closeBtn: 0
                                ,btn: "重新删除"
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
                            ,btn: "重新删除"
                            ,skin: 'layui-layer-molv'
                            ,icon :2
                            ,yes: function (index, layero) {
                                layer.close(index);
                            }
                        });
                    }
                })
            });
        } else if(layEvent === 'update'){ //编辑
            $("#notificationTitle").val(data.notificationTitle);
            $("#notificationContent").val(data.notificationContent);
            $("select[name=status]").empty();
            $("select[name=stick]").empty();
            $("select[name=messageType]").empty();
            $("select[name=status]").append(option1);
            $("select[name=stick]").append(option2);
            $("select[name=messageType]").append(option3);
            $("#status").val(data.status);
            $("#stick").val(data.stick);
            $("#messageType").val(data.messageType);
            $("#messageAttachment").val(data.messageAttachment);
            if(data.messageAttachment.indexOf("AbCdEfG")!=-1){
                $("#messageAttachment").attr("readonly",true);
            }
            $("#id").val(data.id);
            form.render('select');
            layer.open({
                title: '修改'
                ,type:1
                ,moveOut:true
                ,area:["700px","320px"]
                ,skin: 'layui-layer-molv'
                ,content: $("#open-div-update-daily")
            });
        } else if(layEvent === 'shield'){//屏蔽
            layer.confirm('确认屏蔽嘛，屏蔽后不再显示', {icon: 3, title:'提示'}, function(index){
                var param = {"id":data.id};
                $.ajax({
                    type : "post",
                    url : "/publish/shield",
                    dataType : "json",
                    contentType : "application/json",
                    data : JSON.stringify(param),
                    success : function(data) {
                        if (data.success) {
                            layer.msg('屏蔽成功', {
                                offset: '20px'
                                ,icon: 1
                                ,time: 1000
                            }, function(){
                                window.location.reload();
                                // parent.location.reload();
                            });
                        } else {
                            layer.open({
                                title: 'ERROR'
                                ,content: data.msg
                                ,closeBtn: 0
                                ,btn: "重新屏蔽"
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
                            ,btn: "重新屏蔽"
                            ,skin: 'layui-layer-molv'
                            ,icon :2
                            ,yes: function (index, layero) {
                                layer.close(index);
                            }
                        });
                    }
                })
            });
        }
    });

    // 刷新
    $('#btn-refresh').on('click', function () {
        tableIns.reload();
    });

    $('#btn-add').on('click',function () {
        $("select[name=status]").empty();
        $("select[name=stick]").empty();
        $("select[name=messageType]").empty();
        $("select[name=status]").append(option1);
        $("select[name=stick]").append(option2);
        $("select[name=messageType]").append(option3);
        form.render('select');
        layer.open({
            title: '新增'
            ,type:1
            ,moveOut:true
            ,area:["700px","320px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-add-daily")
        });
    });

    form.on('submit(addDaily)', function(data){
        $("#submit").hide();
        $.post('/publish/add',data.field,function (res) {
            if(res.success){
                layer.msg('新增公告成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/toIndex";
                    window.location.reload();
                    // parent.location.reload();
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

    form.on('submit(updateDaily)', function(data){
        $("#submit").hide();
        $.post('/publish/update',data.field,function (res) {
            if(res.success){
                layer.msg('修改公告成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/toIndex";
                    window.location.reload();
                    // parent.location.reload();
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