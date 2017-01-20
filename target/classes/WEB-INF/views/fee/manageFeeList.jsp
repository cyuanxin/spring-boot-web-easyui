<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp" %>
    <script src="${ctx}/static/plugins/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
<div id="tb" style="padding:5px;height:auto">
    <div>

        <input type="text" id="merchantNameFilter" name="filter_LIKES_merchantName" class="easyui-validatebox"
               data-options="width:150,validType:'length[2,20]',prompt: '商户名称'"/>
        <input type="text" id="fundCodeFilter" name="filter_LIKES_fundCode" class="easyui-validatebox"
               data-options="width:150,prompt: '基金代码',validType:'length[1,10]'"/>

        <input type="text" id="startDateFilter" name="filter_GTD_startDate" class="easyui-my97"
               datefmt="yyyy-MM-dd" data-options="width:100,prompt: '开始日期'"/>

        <input type="text" id="endDateFilter" name="filter_LTD_endDate" class="easyui-my97"
               datefmt="yyyy-MM-dd" data-options="width:100,prompt: '结束日期'"/>

        <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>

    </div>

</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">
    var dg;
    var d;
    $(function () {
        dg = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/fee/managefee/json',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            striped: true,
            pagination: true,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            columns: [[
                {field: 'id', title: 'id', hidden: true},
                {field: 'merchantId', title: '商户编号', sortable: true, width: 100},
                {field: 'merchantName', title: '商户名', sortable: true, width: 100},
                {field: 'fundName', title: '基金名字', sortable: true, width: 100},
                {field: 'fundCode', title: '基金代码', sortable: true, width: 100},
                {field: 'fundTypeStr', title: '基金类型', sortable: true, width: 100},
                {field: 'feeDate', title: '费用归属时间', sortable: true, width: 100, formatter:formatDate},
                {field: 'nav', title: '最新净值', sortable: true, width: 100},
                {field: 'navDate', title: '净值日期', sortable: true, width: 100, formatter:formatDate},
                {field: 'holdAmount', title: '保有量', sortable: true, width: 100},
                {field: 'serviceFee', title: '销售服务费（元）', sortable: true, width: 100},
                {field: 'maintainFee', title: '客户维护费（元）', sortable: true, width: 100},
                {field: 'feeTypeStr', title: '费用类型', sortable: true, width: 100},

            ]],
            headerContextMenu: [
                {
                    text: "冻结该列", disabled: function (e, field) {
                    return dg.datagrid("getColumnFields", true).contains(field);
                },
                    handler: function (e, field) {
                        dg.datagrid("freezeColumn", field);
                    }
                },
                {
                    text: "取消冻结该列", disabled: function (e, field) {
                    return dg.datagrid("getColumnFields", false).contains(field);
                },
                    handler: function (e, field) {
                        dg.datagrid("unfreezeColumn", field);
                    }
                }
            ],
            enableHeaderClickMenu: true,
            enableHeaderContextMenu: true,
            enableRowContextMenu: false,
            toolbar: '#tb'
        });
    });

    function cx() {
        var isValid = $('#merchantNameFilter').validatebox("isValid");
        if (!isValid) {
            return;
        }

        var isValid2 = $('#fundCodeFilter').validatebox("isValid");
        if (!isValid2) {
            return;
        }

        var merchantName = $('#merchantNameFilter').val();
        var fundCode = $('#fundCodeFilter').val();
        var startDate = $('#startDateFilter').combobox('getValue');
        var endDate = $('#endDateFilter').combobox('getValue');

        dg.datagrid('reload', {
            filter_LIKES_merchantName:merchantName,
            filter_LIKES_fundCode:fundCode,
            filter_GTD_startDate:startDate,
            filter_LTD_endDate:endDate,
        });
    }

</script>
</body>
</html>