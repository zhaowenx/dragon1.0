layui.use(['form', 'laydate'], function(){
    var form = layui.form
        ,layer = layui.layer
        ,laydate = layui.laydate
        ,$ = layui.$;

    var option1;

    //日期
    laydate.render({
        elem: '#hireDate'
    });

    var hobby=$("#hobby").val();
    var groupCheckbox = $("input[name='hobby']");
    for (i = 0; i < groupCheckbox.length; i++) {
        var val =groupCheckbox[i].value;
        if(hobby.split(',').indexOf(val)!=-1){
            groupCheckbox[i].checked=true;
        }
    }
    form.render();

    //提交
    form.on('submit(replenishInformation)', function(data){
        var arr = new Array();
        $("input:checkbox[name='hobby']:checked").each(function(i){
            arr[i] = $(this).val();
        });
        data.field.hobby = arr.join(",");//将数组合并成字符串
        $("#submit").hide();
        $.post('/user/updateUser',data.field,function (res) {
            if(res.success){
                layer.msg('修改用户信息成功', {
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
                    $("select[name=profession]").empty();
                    $("select[name=profession]").append(option1);
                    $("#profession").val($("#profession1").val());
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
        getItemVal("profession");
        addSelect('provinceName',defaultDate.province,false,'code','name','000000');
    })

    // 一级联动事件触发
    form.on('select(province)', function(data){
        var value = data.value;
        areaState.province.fn(value);
    });

    // 二级联动事件触发
    form.on('select(city)', function(data){
        var value = data.value;
        areaState.city.fn(value);
    });

    /**
     * @param id 下拉框id
     * @param data 下拉框数据
     * @param bool 是否添加“----请选择----”选项
     * @param val 代码
     * @param text  名称
     * @param region
     */
    function addSelect(id,data,bool,code,name,parent1){
        code = code || 'code';
        name = name || 'name';
        var parent = 'parent';
        var html = '';
        var $id = $('#'+id);
        $id.html('');
        if(bool){
            html += '<option value="">----请选择----</option>';
        }
        for(var i in data){
            if (parent1 != "000000"){
                if(data[i][parent] == parent1){
                    html +='<option value="'+data[i][code]+'">'+data[i][name]+'</option>';
                }
            }else{
                html +='<option value="'+data[i][code]+'">'+data[i][name]+'</option>';
            }
        }
        $id.html(html);
        form.render("select");
    }

    var areaState = {
        province: {
            state: false,
            data: [],
            fn: function(value){ // value 表示的是区域代码，及传到后台的数据
                this.state = true; // 可能以后会用到
                areaState.city.state = false; // 可能以后会用到
                areaState.district.state = false; // 可能以后会用到

                // 从后台调用真实接口
                // get(function(data){
                //    areaState.street.data = data;
                //    console.log(areaState.street.data);
                //    addSelect('street select',areaState.street.data,false,'regionCode','regionName');
                // }

                areaState.city.data = defaultDate.city;
                if(value != '') {
                    addSelect('cityName',areaState.city.data,false,'code','name',value);
                } else {
                    $('#cityName').html('');
                    $('#countyName').html('');
                    form.render("select");
                }
            }
        },
        city: {
            state: false,
            data: [],
            fn: function(value){
                this.state = true;
                areaState.district.state = false;
                areaState.district.data = defaultDate.district;
                if(value != '') {
                    addSelect('countyName',areaState.district.data,false,'code','name',value);
                } else {
                    $('#countyName').html('');
                    form.render("select");
                }
            }
        },
        district: {
            state: false,
            data: [],
            fn: function(){
                this.state = true;
            }
        }
    };
});