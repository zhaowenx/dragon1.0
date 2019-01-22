layui.use(['table', 'form', 'layer', 'vip_table','laydate'], function () {

    // 操作对象
    var form = layui.form
        , table = layui.table
        , layer = layui.layer
        , vipTable = layui.vip_table
        ,laydate = layui.laydate
        , $ = layui.jquery;

    var option1;

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
                if("material_type" == dict){
                    option1 = option;
                    $("select[name=type]").empty();
                    $("select[name=type]").append(option1);
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
        getItemVal("material_type");
    })

    $("#close-btn-qx").click(function () {
        $("#open-div-add-daily").hide();
    });

    $("#user-defined-select").click(function () {
        tableIns.reload({
            where:{
                "description":$("#description_s").val(),
                "type":$("#type_s").val()
            }
        });
    });

    $("#user-defined-reset").click(function () {
        $("#description_s").val('');
        $("#type_s").val('');
        // tableIns.reload({
        //     where:{
        //         "dailyDate":$("#daily_date_s").val(),
        //         "isEvection":$("#is_evection_s").val()
        //     }
        // });
    })

    // 表格渲染
    var tableIns = table.render({
        elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            {field: 'description', title: '描述', width: 380}
            ,{field: 'url', title: '地址', width: 450}
            , {field: 'type', title: '分类', width: 80,templet:function (data) {
                if (data.type ==='1'){
                    return "视频";
                }else if(data.type ==='2'){
                    return "资料";
                }else if(data.type ==='3'){
                    return "kayak";
                }else{
                    return "其他";
                }
            }}
            , {field: 'updateTime', title: '更新时间', width: 180}
            , {field: 'right1', title: '操作', width: 202, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
        ]]
        , id: 'dataCheck'
        , url: '/material/show'
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

    table.on('tool(dateTable)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'delete'){ //删除
            layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
                var param = {"id":data.id};
                $.ajax({
                    type : "post",
                    url : "/material/delete",
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
            $("#description").val(data.description);
            $("#url").val(data.url);
            $("select[name=type]").empty();
            $("select[name=type]").append(option1);
            $("#type").val(data.type);
            $("#id").val(data.id);
            form.render('select');
            layer.open({
                title: '修改'
                ,type:1
                ,moveOut:true
                ,area:["700px","330px"]
                ,skin: 'layui-layer-molv'
                ,content: $("#open-div-update-material")
            });
        }
    });

    // 刷新
    $('#btn-refresh').on('click', function () {
        tableIns.reload();
    });

    $('#btn-add').on('click',function () {
        $("select[name=type]").empty();
        $("select[name=type]").append(option1);
        form.render('select');
        layer.open({
            title: '新增'
            ,type:1
            ,moveOut:true
            ,area:["700px","330px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-add-material")
        });
    });

    form.on('submit(addMaterial)', function(data){
        $("#submit").hide();
        $.post('/material/add',data.field,function (res) {
            if(res.success){
                layer.msg('新增资料成功', {
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

    form.on('submit(updateMaterial)', function(data){
        $("#submit").hide();
        $.post('/material/update',data.field,function (res) {
            if(res.success){
                layer.msg('修改资料成功', {
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