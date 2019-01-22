layui.use(['form', 'layer'], function () {

    // 操作对象
    var form = layui.form
        , layer = layui.layer
        , $ = layui.jquery;

    // 提交监听
    form.on('submit(submitWordpad)', function (data) {
        $.post('/wordpad/save',data.field,function (res) {
            if(res.success){
                layer.msg(res.msg, {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/logout";
                    window.location.reload();
                    // parent.location.href="/login/logout"
                });
            }else{
                layer.open({
                    title: 'ERROR'
                    ,content: res.msg
                    ,closeBtn: 0
                    ,btn: "重新发布"
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

    form.on('submit(updateWordpad)',function (data) {
        $.post('/wordpad/updateWordpad',data.field,function (res) {
            if(res.success){
                layer.msg(res.msg, {
                    offset: '20px'
                    ,icon: 1
                    ,time: 1000
                }, function(){
                    // window.location.href="/login/logout";
                    window.location.reload();
                    // parent.location.href="/login/logout"
                });
            }else{
                layer.open({
                    title: 'ERROR'
                    ,content: res.msg
                    ,closeBtn: 0
                    ,btn: "修改失败"
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

$(function () {
    initLayPage();
    $("#close-btn-qx").click(function () {
        $("#open-div-add-wordpad").hide();
    });
});

function initLayPage (pageMake) {
    if(!pageMake){
        pageMake={};
        pageMake.pageSize = 5;
        pageMake.currentPage = 1;
    }

    var param = {"pageSize":pageMake.pageSize,"currentPage":pageMake.currentPage};

    $.post("/wordpad/showWordPad", param, function (data) {
        layui.use(['laypage', 'layer'], function () {
            var laypage = layui.laypage;
            laypage.render({
                elem: 'lay-wordpad-list',
                count: data.data.totalRecord,
                curr: pageMake.currentPage,
                limit: pageMake.pageSize,
                limits:[10, 20, 30, 40, 50],
                first:"首页",
                last:"尾页",
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip'],
                jump: function (obj, first) {
                    if (!first) {
                        pageMake.currentPage = obj.curr;
                        pageMake.pageSize = obj.limit;
                        initLayPage(pageMake);
                    }
                }
            });
            fillTable(data.data.dataList); //页面填充
        })
    });
}

function fillTable(data) {
    var info = '';
    $.each(data, function (index, obj) {
        info += '<tr><td>' + obj.title + '</td><td>' + obj.content + '</td><td>' + obj.createTime + '</td><td>' + obj.updateTime +'</td>'+
            '<td style="text-align: center;"><button class="layui-btn layui-btn-normal layui-btn-radius" id="lay-btn-update" ' +
            'onclick="updateContent('+obj.id+')"><i class="layui-icon">&#xe642;</i></button>' +
            '<button  class="layui-btn layui-btn-danger layui-btn-radius" id="lay-btn-delete" onclick="deleteWordpad('+obj.id+')"><i class="layui-icon">&#xe640;</i></button>'+ '</td></tr>';
    });
    $("#tab_list").html(info);
}

function deleteWordpad(id) {
    layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
        var param = {"id":id};
        $.ajax({
            type : "post",
            url : "/wordpad/deleteWordpad",
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
}

function updateContent(id){
    var param = {"id":id};
    $.ajax({
        type : "post",
        url : "/wordpad/selectWordpadById",
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(param),
        success : function(data) {
            if (data.success) {
                $("#title").val(data.data.title);
                $("#content").val(data.data.content);
                $("#id").val(data.data.id);
                layer.open({
                    title: '修改'
                    ,type:1
                    ,moveOut:true
                    ,area:["730px","360px"]
                    ,skin: 'layui-layer-molv'
                    ,content: $("#open-div-add-wordpad")
                });
            } else {
                layer.open({
                    title: 'ERROR'
                    ,content: data.data.msg
                    ,closeBtn: 0
                    ,btn: "取消"
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