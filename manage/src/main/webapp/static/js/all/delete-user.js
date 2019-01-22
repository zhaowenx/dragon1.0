layui.use(['table', 'form', 'layer', 'vip_table'], function () {

    // 操作对象
    var form = layui.form
        , table = layui.table
        , layer = layui.layer
        , vipTable = layui.vip_table
        , $ = layui.jquery;

    // 表格渲染
    var tableIns = table.render({
        elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
        , height: vipTable.getFullHeight()    //容器高度
        , cols: [[                  //标题栏
            {field: 'userName', title: '用户名', width: 200}
            ,{field: 'realName', title: '真实姓名', width: 200}
            ,{field: 'mobile', title: '手机号', width: 150}
            ,{field: 'isSuperUser', title: '用户类别', width: 100,templet:function (data) {
                if (data.isSuperUser ===1){
                    return "超级用户";
                }else if(data.isSuperUser ===0){
                    return "普通用户";
                }else{
                    return "其他";
                }
            }}
            ,{field: 'loginTimes', title: '登录次数', width: 100}
            ,{field: 'lastLoginDate', title: '最后一次登录时间', width: 200}
            , {field: 'createTime', title: '创建时间', width: 200}
            , {field: 'right1', title: '操作', width: 135, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
        ]]
        , url: '/user/selectAll'
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

        if(layEvent === 'delete'){ //删除
            layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
                var param = {"id":data.id,"isSuperUser":data.isSuperUser};
                $.ajax({
                    type : "post",
                    url : "/user/deleteUser",
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
                                // window.location.reload();
                                tableIns.reload();
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
        }
    });
});