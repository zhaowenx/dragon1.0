layui.use(['table', 'form', 'layer', 'vip_table','laydate'], function () {

    // 操作对象
    var form = layui.form
        , table = layui.table
        , layer = layui.layer
        , vipTable = layui.vip_table
        ,laydate = layui.laydate
        , $ = layui.jquery;

    var option1;

    //日期
    laydate.render({
        elem: '#dailyDate'
    });
    laydate.render({
        elem: '#dailyDate1'
    });
    laydate.render({
        elem: '#dailyDate2'
    });
    laydate.render({
       elem: '#daily_date_s'
    });

    $("#close-btn-qx").click(function () {
        $("#open-div-add-daily").hide();
    });

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
                if("isEvection" == dict){
                    option1 = option;
                    $("select[name=is_evection]").empty();
                    $("select[name=is_evection]").append(option1);
                    form.render('select');
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
        getItemVal("isEvection");
    })

    // 表格渲染
    var tableIns = table.render({
        elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            // {checkbox: true, fixed: true, space: true,width:80},
            //{field: 'id', title: 'ID', width: 50},
            {field: 'userName', title: '用户名', width: 150}
            , {field: 'content', title: '内容', width: 380}
            , {field: 'isEvection', title: '是否出差', width: 90,templet:function (data) {
                return data.isEvection == 'Y'?'是':'否';
            }}
            , {field: 'dailyDate', title: '日报日期', width: 120}
            , {field: 'createTime', title: '创建时间', width: 170}
            , {field: 'updateTime', title: '更新时间', width: 170}
            , {field: 'right1', title: '操作', width: 208, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
        ]]
        , id: 'dataCheck'
        , url: '/daily/show'
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

    $("#user-defined-select").click(function () {
        tableIns.reload({
            where:{
                "dailyDate":$("#daily_date_s").val(),
                "isEvection":$("#is_evection_s").val()
            }
        });
    });
    
    $("#user-defined-reset").click(function () {
        $("#daily_date_s").val('');
        $("#is_evection_s").val('');
        // tableIns.reload({
        //     where:{
        //         "dailyDate":$("#daily_date_s").val(),
        //         "isEvection":$("#is_evection_s").val()
        //     }
        // });
    })

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
                    url : "/daily/delete",
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
            $("#content").val(data.content);
            $("#dailyDate").val(data.dailyDate);
            $("#id").val(data.id);
            $("#dailyDate").attr("disabled","true");
            $("select[name=isEvection]").empty();
            $("select[name=isEvection]").append(option1);
            $("#isEvection").val(data.isEvection);
            form.render('select');
            layer.open({
                title: '修改'
                ,type:1
                ,moveOut:true
                ,area:["710px","320px"]
                ,skin: 'layui-layer-molv'
                ,content: $("#open-div-update-daily")
            });
        }
    });

    // 刷新
    // $('#btn-refresh').on('click', function () {
    //     tableIns.reload();
    // });
    
    $('#btn-add').on('click',function () {
        $("select[name=isEvection]").empty();
        $("select[name=isEvection]").append(option1);
        form.render('select');
        layer.open({
            title: '新增'
            ,type:1
            ,moveOut:true
            ,area:["710px","320px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-add-daily")
        });
    });

    form.on('submit(addDaily)', function(data){
        $("#submit").hide();
        $.post('/daily/add',data.field,function (res) {
            if(res.success){
                layer.msg('新增日报成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/toIndex";
                    window.location.reload();
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
        $.post('/daily/update',data.field,function (res) {
            if(res.success){
                layer.msg('修改日报成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/toIndex";
                    window.location.reload();
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