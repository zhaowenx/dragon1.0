layui.use(['tree', 'table', 'vip_table', 'layer','form'], function () {
    // 操作对象
    var table = layui.table
        , vipTable = layui.vip_table
        , layer = layui.layer
        , $ = layui.jquery
        , form = layui.form;

    var dict = '';
    var dict_item_dict = '';
    var index_item;
    var index_item_open;
    var index1;

    $("#hide_show").hide();

    var sysDict = table.render({
        elem: '#sys_dict'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            {field: 'dict', title: '字典标识', width: 150}
            , {field: 'dictName', title: '字典名称', width: 150}
            , {field: 'groupDict', title: '字典上级组', width: 100}
            , {field: 'right1', title: '操作', width: 350, align: 'center', toolbar: '#sys_dict_a'} //这里的toolbar值是模板元素的选择器
        ]]
        , url: '/dict/showDict'
        , method: 'get'
        , page: true
        , limits: [10, 20, 30, 40, 50]
        , limit: 10
        , loading: true
        , request: {pageName: 'currentPage', limitName: 'pageSize'}
    });

    table.on('tool(sys_dict)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if(layEvent === 'delete'){ //删除
            layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
                var param = {"dict":data.dict};
                $.ajax({
                    type : "post",
                    url : "/dict/deleteDict",
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
            $("#dict").val(data.dict);
            $("#dictName").val(data.dictName);
            layer.open({
                title: '修改(字典标识不能修改)'
                ,type:1
                ,moveOut:true
                ,area:["700px","200px"]
                ,skin: 'layui-layer-molv'
                ,content: $("#open-div-update-dict")
            });
        } else if(layEvent === 'detail'){//显示子项信息
            $("#hide_show").show();
            dict = data.dict;
            dict_item_dict = data.dict;
            $("#show_dict").html(dict);
            table.render({
                elem: '#sys_dict_item'                  //指定原始表格元素选择器（推荐id选择器）
                , height: vipTable.getFullHeight()    //容器高度
                , cols: [[                  //标题栏
                    {field: 'itemKey', title: '子项值', width: 100}
                    , {field: 'itemVal', title: '子项名', width: 200}
                    , {field: 'right1', title: '操作', width: 230, align: 'center', toolbar: '#sys_dict_item_a'} //这里的toolbar值是模板元素的选择器
                ]]
                , url: '/dict/showDictItem?dict='+dict
                , method: 'get'
            });
        }
    });

    // 表格渲染
    var sysDictItem = table.render({
        elem: '#sys_dict_item'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            {field: 'itemKey', title: '子项值', width: 100}
            , {field: 'itemVal', title: '子项名', width: 200}
            , {field: 'right1', title: '操作', width: 230, align: 'center', toolbar: '#sys_dict_item_a'} //这里的toolbar值是模板元素的选择器
        ]]
        , url: '/dict/showDictItem?dict='+dict
        , method: 'get'
    });

    $('#btn-refresh').on('click', function () {
        $("#hide_show").hide();
        sysDict.reload();
        sysDictItem.reload();
    });

    $('#btn-add-dict').on('click',function () {
        $("select[name=groupDict]").empty();
        $.ajax({
            type : "post",
            url : "/dict/selectGroupDict",
            contentType : "application/json",
            success : function(data) {
                if (data.success) {
                    var option = '';
                    for(var i=0;i<data.data.length;i++){
                        option +="<option value=\""+data.data[i].dict+"\">"+data.data[i].dict+"-"+data.data[i].dictName+"</option>"; //动态添加数据
                    }
                    $("select[name=groupDict]").append(option);
                    form.render('select');
                    index1 = layer.open({
                        title: '新增数据字典信息'
                        ,type:1
                        ,moveOut:true
                        ,area:["700px","200px"]
                        ,skin: 'layui-layer-molv'
                        ,content: $("#open-div-add-dict")
                    });
                } else {
                    layer.open({
                        title: 'ERROR'
                        ,content: data.msg
                        ,closeBtn: 0
                        ,btn: "离开"
                        ,skin: 'layui-layer-molv'
                        ,icon :2
                        ,yes: function (index, layero) {
                            layer.close(index);
                        }
                    });
                }
            }
        })
    });

    $('#btn-add-dict-item').on('click',function () {
        $("#dict-item-dict").val(dict_item_dict);
        $("#itemKey").val('');
        $("#itemVal").val('');
        index_item = layer.open({
            title: '新增数据字典子项信息'
            ,type:1
            ,moveOut:true
            ,area:["700px","200px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-add-dict-item")
        });
    });

    form.on('submit(addDict)', function(data){
        $.post('/dict/addDict',data.field,function (res) {
            if(res.success){
                layer.msg('新增数据字典成功', {
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
                        layer.close(index);
                    }
                });
            }
        });
        return false;
    });

    form.on('submit(updateDict)', function(data){
        $.post('/dict/updateDict',data.field,function (res) {
            if(res.success){
                layer.msg('修改数据字典成功', {
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
                        layer.close(index);
                    }
                });
            }
        });
        return false;
    });

    form.on('submit(addDictItem)', function(data){
        $.post('/dict/addDictItem',data.field,function (res) {
            if(res.success){
                layer.msg('新增数据字典子项成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    layer.close(index_item);
                    table.render({
                        elem: '#sys_dict_item'                  //指定原始表格元素选择器（推荐id选择器）
                        , height: vipTable.getFullHeight()    //容器高度
                        , cols: [[                  //标题栏
                            {field: 'itemKey', title: '子项值', width: 100}
                            , {field: 'itemVal', title: '子项名', width: 200}
                            , {field: 'right1', title: '操作', width: 230, align: 'center', toolbar: '#sys_dict_item_a'} //这里的toolbar值是模板元素的选择器
                        ]]
                        , url: '/dict/showDictItem?dict='+dict
                        , method: 'get'
                    });
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

    table.on('tool(sys_dict_item)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）

        if(layEvent === 'delete'){ //删除
            layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
                var param = {"dict":data.dict,"itemKey":data.itemKey};
                $.ajax({
                    type : "post",
                    url : "/dict/deleteDictItem",
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
                                table.render({
                                    elem: '#sys_dict_item'                  //指定原始表格元素选择器（推荐id选择器）
                                    , height: vipTable.getFullHeight()    //容器高度
                                    , cols: [[                  //标题栏
                                        {field: 'itemKey', title: '子项值', width: 100}
                                        , {field: 'itemVal', title: '子项名', width: 200}
                                        , {field: 'right1', title: '操作', width: 230, align: 'center', toolbar: '#sys_dict_item_a'} //这里的toolbar值是模板元素的选择器
                                    ]]
                                    , url: '/dict/showDictItem?dict='+dict
                                    , method: 'get'
                                });
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
            $("#dict-item-dict-item").val(data.dict);
            $("#itemKey").val(data.itemKey);
            $("#itemVal").val(data.itemVal);
            $("#oldItemKey").val(data.itemKey);
            index_item_open = layer.open({
                title: '修改数据字典子项信息(字典标识不能修改)'
                ,type:1
                ,moveOut:true
                ,area:["700px","200px"]
                ,skin: 'layui-layer-molv'
                ,content: $("#open-div-update-dict-item")
            });
        }
    });

    form.on('submit(updateDictItem)', function(data){
        $.post('/dict/updateDictItem',data.field,function (res) {
            if(res.success){
                layer.msg('修改数据字典子项信息成功', {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    layer.close(index_item_open);
                    table.render({
                        elem: '#sys_dict_item'                  //指定原始表格元素选择器（推荐id选择器）
                        , height: vipTable.getFullHeight()    //容器高度
                        , cols: [[                  //标题栏
                            {field: 'itemKey', title: '子项值', width: 100}
                            , {field: 'itemVal', title: '子项名', width: 200}
                            , {field: 'right1', title: '操作', width: 230, align: 'center', toolbar: '#sys_dict_item_a'} //这里的toolbar值是模板元素的选择器
                        ]]
                        , url: '/dict/showDictItem?dict='+dict
                        , method: 'get'
                    });
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
});

