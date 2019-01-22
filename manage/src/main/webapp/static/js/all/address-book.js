layui.use(['form', 'layer'], function () {

    // 操作对象
    var form = layui.form, layer = layui.layer, $ = layui.jquery;

    var option1;
    var option2;
    var option3;

    $('#btn-refresh').on('click', function () {
        window.location.reload();
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
                if("profession" == dict){
                    option1 = option;
                }else if("type" == dict){
                    option2 = option;
                }else if("sex" == dict){
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

    //初始化下拉框列表
    $(function () {
        getItemVal("profession");
        getItemVal("type");
        getItemVal("sex");
    })

    $('#btn-add').on('click',function () {
        $("select[name=profession]").empty();
        $("select[name=type]").empty();
        $("select[name=sex]").empty();
        $("select[name=profession]").append(option1);
        $("select[name=type]").append(option2);
        $("select[name=sex]").append(option3);
        form.render('select');
        layer.open({
            title: '新增'
            ,type:1
            ,moveOut:true
            ,area:["700px","500px"]
            ,skin: 'layui-layer-molv'
            ,content: $("#open-div-add-address-book")
        });
    });

    form.on('submit(addAddressBook)', function(data){
        $.post('/address/add',data.field,function (res) {
            if(res.success){
                layer.msg('通讯录添加成功', {
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

    form.on('submit(updateAddressBook)', function(data){
        $.post('/address/update',data.field,function (res) {
            if(res.success){
                layer.msg('通讯录修改成功', {
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

    window.updateAddress = function(id) {
        var param = {"id":id};
        $.ajax({
            type : "post",
            url : "/address/selectAddressBookById",
            contentType : "application/json",
            dataType : "json",
            data : JSON.stringify(param),
            success : function(data) {
                if (data.success) {
                    $("#id").val(data.data.id);
                    $("#chineseName").val(data.data.chineseName);
                    $("#englishName").val(data.data.englishName);
                    $("#anotherName").val(data.data.anotherName);
                    $("#qqNumber").val(data.data.qqNumber);
                    $("#weixin").val(data.data.weixin);
                    $("#domicile").val(data.data.domicile);
                    $("#address").val(data.data.address);
                    $("#email").val(data.data.email);
                    $("#phone").val(data.data.phone);
                    $("#weibo").val(data.data.weibo);
                    $("#birthday").val(data.data.birthday);
                    $("select[name=profession]").empty();
                    $("select[name=type]").empty();
                    $("select[name=sex]").empty();
                    $("select[name=profession]").append(option1);
                    $("select[name=type]").append(option2);
                    $("select[name=sex]").append(option3);
                    form.render('select');
                    $("#sex").val(data.data.sex);
                    $("#profession").val(data.data.profession);
                    $("#type").val(data.data.type);
                    form.render('select');
                    layer.open({
                        title: '编辑'
                        ,type:1
                        ,moveOut:true
                        ,area:["700px","500px"]
                        ,skin: 'layui-layer-molv'
                        ,content: $("#open-div-update-address-book")
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

    window.deleteAddress = function(id) {
        layer.confirm('确认删除嘛，删除后不可恢复', {icon: 3, title:'提示'}, function(index){
            var param = {"id":id};
            $.ajax({
                type : "post",
                url : "/address/delete",
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
});

// function setSelectChecked(selectId, checkValue){
//     var select = document.getElementById(selectId);
//     for(var i=0; i<select.options.length; i++){
//         if(select.options[i].innerHTML == checkValue){
//             select.options[i].selected = true;
//             break;
//         }
//     }
// };

