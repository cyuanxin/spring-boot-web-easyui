/**
 * Created by stark.zhang on 2016/6/1.
 */
function formatDate(value, row) {
    if (value == null) {
        return '';
    }

    var d = new Date(value);
    return $.fn.datebox.defaults.formatter(d);
}

function toPercent(value, row) {
    if (value == null || value == '') {
        return '';
    }
    if (value == 0) {
        return '0';
    }
    numberval = Math.round(value*100 * 100) / 100;
    return numberval + '%';
}

function formatDateTime(value, row) {
    var d = new Date(value);
    var yyyy = d.getFullYear();
    var mm = d.getMonth() < 9 ? "0" + (d.getMonth() + 1) : (d.getMonth() + 1); // getMonth() is zero-based
    var dd = d.getDate() < 10 ? "0" + d.getDate() : d.getDate();
    var hh = d.getHours() < 10 ? "0" + d.getHours() : d.getHours();
    var min = d.getMinutes() < 10 ? "0" + d.getMinutes() : d.getMinutes();
    return "".concat(yyyy).concat(mm).concat(dd).concat(":").concat(hh).concat(min);
}



function getManagerId(inputId,url) {
    var manager = $('#' + inputId).val();
    if (manager == '') {
        $.messager.alert('Warning', '搜索字段不允许空');
        return;
    }
    $.ajax({
        url: url,
        method: 'post',
        async: false,
        data: {
            manager: manager
        },
        success: function (data) {
            $.messager.alert('msg', converManagersListToStr(data));
        }
    });
}

function converManagersListToStr(data) {
    var len = data.length;
    if (len == 0) {
        return "no data";
    }
    var text = "<ul>";
    for (i = 0; i < len; i++) {
        text += "<li>" + 'id:' + data[i].personnelId +' name:' + data[i].personnelName + "</li>";
    }
    return text;
}

function getManagers(dialogId,searchId,inputId,urlHead) {
    var manager = $('#' + searchId).val();
    if (manager == '') {
        $.messager.alert('Warning', '搜索字段不允许空');
        return;
    }

    $("#"+dialogId).dialog({
        title: '基金经理',
        width: 500,
        height: 400,
        href: urlHead + '/fundManager/listInit/name='+manager,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确定',
            handler: function () {
                var row = $('#managersDG').datagrid('getSelected');
                if(rowIsNull(row)) {
                    $.messager.alert('Warning', '没选择行');
                    $("#"+dialogId).panel('close');
                    return;
                }
                var id = row.id;
                console.log('id:' + id)
                $('#'+inputId).val(id);
                $("#"+dialogId).panel('close');
            }
        },{
            text: '取消',
            handler: function () {
                $("#"+dialogId).panel('close');
            }
        }]
    });
}

function getCompanys(dialogId,searchId,inputId,urlHead) {
    var company = $('#' + searchId).val();
    if (company == '') {
        $.messager.alert('Warning', '搜索字段不允许空');
        return;
    }

    $("#"+dialogId).dialog({
        title: '基金公司',
        width: 500,
        height: 400,
        href: urlHead + '/company/listInit?filter_LIKES_name='+company,
        maximizable: true,
        modal: true,
        buttons: [{
            text: '确定',
            handler: function () {
                var row = $('#companysDG').datagrid('getSelected');
                if(rowIsNull(row)) {
                    $.messager.alert('Warning', '没选择行');
                    $("#"+dialogId).panel('close');
                    return;
                }
                var id = row.id;
                console.log('id:' + id)
                $('#'+inputId).val(id);
                $("#"+dialogId).panel('close');
            }
        },{
            text: '取消',
            handler: function () {
                $("#"+dialogId).panel('close');
            }
        }]
    });

}

function getDataGridSelectes(dgId) {
    var ids = [];
    var rows = $('#'+dgId).datagrid('getChecked');

    var len = rows.length;
    for(var i=0; i<len; i++){
        ids.push(rows[i].id);
    }
    return ids.join();
}

function getTotalNum() {
    var options = $('#dg').datagrid('getPager').data("pagination").options;
    return options.total;
}

function converCompanyListToStr(data) {
    var len = data.length;
    if (len == 0) {
        return "no data";
    }
    var text = "<ul>";
    for (i = 0; i < len; i++) {
        text += "<li>" + 'id:' + data[i].companyId +' name:' + data[i].companyName + "</li>";
    }
    return text;
}
var showScrView = $.extend({}, $.fn.datagrid.defaults.view, {
    onAfterRender: function (target) {
        $.fn.datagrid.defaults.view.onAfterRender.call(this, target);
        var dg = $(target);
        if (dg.datagrid('getRows').length == 0) {
            setTimeout(function () {
                dg.datagrid('appendRow', {});
                dg.datagrid('options').finder.getTr(target, 0).css('height', 0);
            }, 0)
        }
    }
});

