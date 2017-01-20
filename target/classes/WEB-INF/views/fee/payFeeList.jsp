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
        <form id="searchFrom" action="">
            <input type="text" id="merchantNameFilter" name="filter_LIKES_merchantName" class="easyui-validatebox"
                   data-options="width:150,validType:'length[2,20]',prompt: '商户名称'"/>
            <input type="text" id="fundCodeFilter" name="filter_LIKES_fundCode" class="easyui-validatebox"
                   data-options="width:150,prompt: '基金代码',validType:'length[1,10]'"/>
            <input type="text" id="serialNoFilter" name="filter_LIKES_serialNo" class="easyui-validatebox"
                   data-options="width:150,prompt: '申请单号',validType:'length[1,30]'"/>

            <input type="checkbox" name="filter_LIKES_showAll" value="1" id="showAllFilter"><label for="showAllFilter">显示所有</label>

            <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
        </form>


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
            url: '${ctx}/fee/payfee/json',
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
                {field: 'serialNo', title: '申请单号', sortable: true, width: 200},
                {field: 'userName', title: '姓名', sortable: true, width: 100},
                {field: 'fundName', title: '基金名字', sortable: true, width: 100},
                {field: 'fundCode', title: '基金代码', sortable: true, width: 100},
                {field: 'fundTypeStr', title: '基金类型', sortable: true, width: 100},
                {field: 'businessTypeStr', title: '业务类型', sortable: true, width: 100},
                {field: 'operDate', title: '交易时间', sortable: true, width: 100, formatter:formatDate},
                {field: 'bankCode', title: '银行代码', sortable: true, width: 100},
                {field: 'channelStr', title: '支付渠道', sortable: true, width: 100},
                {field: 'cancelStr', title: '撤单', sortable: true, width: 100},
                {field: 'refundStr', title: '退款', sortable: true, width: 100},
                {field: 'payFee', title: '支付费用（元）', sortable: true, width: 100},
                {field: 'cancelFee', title: '撤单费用（元）', sortable: true, width: 100},
                {field: 'refundFee', title: '退款费用（元）', sortable: true, width: 100},
                {field: 'thirdPartyFee', title: '第三方支付费用（元）', sortable: true, width: 100},
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

        var isValid3 = $('#serialNoFilter').validatebox("isValid");
        if (!isValid3) {
            return;
        }

        var merchantName = $('#merchantNameFilter').val();
        var fundCode = $('#fundCodeFilter').val();
        var serialNo = $('#serialNoFilter').val();
        var showAll = $('#showAllFilter').is(':checked');

        dg.datagrid('reload', {
            filter_LIKES_merchantName:merchantName,
            filter_LIKES_fundCode:fundCode,
            filter_LIKES_serialNo:serialNo,
            filter_LIKES_showAll:showAll,
        });
    }

</script>
</body>
</html>