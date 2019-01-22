layui.use(['vip_nav','jquery'], function () {
    var vipNav = layui.vip_nav
        ,$ = layui.jquery
        ,layer = layui.layer;
    // 顶部左侧菜单生成 [请求地址,过滤ID,是否展开,携带参数]
    // vipNav.top_left('../static/json/nav_top_left.json','side-top-left',false);
    // 主体菜单生成 [请求地址,过滤ID,是否展开,携带参数]
    // var addr = '';
    // var url = '/menu/getMenu';
    var url = '../static/json/nav_main.json';
    var interval;
    // var ids = {};
    //
    // $.post(url, ids, function (res) {
    //     if (res.success) {
    //         // addr = JSON.parse(res.data);
    //         addr = $.parseJSON(res.data);
    //         addr = JSON.stringify(addr);
    //     } else {
    //         layer.msg(res.msg);
    //     }
    // });

    // $.post({
    //     type : "post",
    //     url : "/menu/getMenu",
    //     dataType : "json",
    //     contentType : "application/json",
    //     // data : JSON.stringify(param),
    //     success : function(data) {
    //         if (data.success) {
    //             addr = data.data;
    //         } else {
    //             layer.msg(data.msg);
    //         }
    //     },
    //     error : function(xmlq, errq) {
    //         layer.msg(errq);
    //     }
    // })

    // vipNav.main('../static/json/nav_main.json','side-main',false);
    vipNav.main(url,'side-main',true);
    // vipNav.top_left('../static/json/nav_top_left.json','side-top-left',false);





    $(function () {
        time1();

        //循环执行，每隔1秒钟执行一次 1000
        interval=window.setInterval(refreshCount, 60000*20);
    })

    function refreshCount() {
        window.location.href="toIndex";
    }
});

function time1(){
    var date = new Date();
    var month = date.getMonth()+1;
    var year=date.getFullYear();
    var day=date.getDate();
    var week = date.getDay();
    var hour = date.getHours();
    var min = date.getMinutes();
    var sec = date.getSeconds();
    var week1;
    switch(week)
    {
        case 0: week1='星期日'; break;
        case 1: week1='星期一'; break;
        case 2: week1='星期二'; break;
        case 3: week1='星期三'; break;
        case 4: week1='星期四'; break;
        case 5: week1='星期五'; break;
        case 6: week1='星期六'; break;
    }
    var time = year+'-'+month+'-'+day+'  '+' '+hour+':'+min+':'+sec+' '+week1;
    $("#layui-date-now").html(time);
    setTimeout(time1,1000);
}

function showPublish(id) {
    var param = {"id":id};
    $.ajax({
        type : "post",
        url : "/publish/showPublishDetail",
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(param),
        success : function(data) {
            if (data.success) {
                // $("#notificationTitle").val(data.data.notificationTitle);
                $("#notificationContent").val(data.data.notificationContent);
                // $(".layui-layer-shade").hide();
                var index1 = layer.open({
                    title: data.data.notificationTitle
                    ,type:1
                    ,moveOut:true
                    ,area:["700px","210px"]
                    ,skin: 'layui-layer-molv'
                    ,content: $("#open-div-show-publish")
                    ,cancel: function(){
                        $(".layui-layer-shade").hide();
                        $("#notificationContent").val('');
                        layer.close(index1);
                        // window.location.reload();
                    }
                });
            } else {
                layer.open({
                    title: 'ERROR'
                    ,content: data.msg
                    ,closeBtn: 0
                    ,btn: "关闭"
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
                ,btn: "关闭"
                ,skin: 'layui-layer-molv'
                ,icon :2
                ,yes: function (index, layero) {
                    layer.close(index);
                }
            });
        }
    })
    layer.open({
        title: '修改'
        ,type:1
        ,moveOut:true
        ,area:["700px","320px"]
        ,skin: 'layui-layer-molv'
        ,content: $("#open-div-update-daily")
    });
}

