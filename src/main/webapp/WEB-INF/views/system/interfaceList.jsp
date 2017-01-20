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

        <!--
        <form id="searchFrom" action="">
            <input type="text" name="filter_LIKES_name" class="easyui-validatebox"
                   data-options="width:150,prompt: '昵称'"/>
            <input type="text" name="filter_LIKES_phone" class="easyui-validatebox"
                   data-options="width:150,prompt: '电话'"/>
            <input type="text" name="filter_GTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd"
                   data-options="width:150,prompt: '开始日期'"/>
            - <input type="text" name="filter_LTD_createDate" class="easyui-my97" datefmt="yyyy-MM-dd"
                     data-options="width:150,prompt: '结束日期'"/>
            <span class="toolbar-item dialog-tool-separator"></span>
            <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
        </form>
        -->

        <shiro:hasPermission name="sys:interface:invoke">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="addOrder();">同步订单</a>
            <span class="toolbar-item dialog-tool-separator"></span>


            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="addManageFee();">计算管理费</a>
            <span class="toolbar-item dialog-tool-separator"></span>

            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="addFeeOverview();">计算费用总览</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        </shiro:hasPermission>
    </div>

</div>
<table id="dg"></table>
<div id="dlg"></div>
<script type="text/javascript">


    function addOrder() {
        d = $("#dlg").dialog({
            title: '同步订单数据',
            width: 380,
            height: 380,
            href: '${ctx}/system/interface/order/invoke',
            maximizable: true,
            modal: true,

            buttons: [{
                text: '确认',
                handler: function () {
                    $("#mainform").submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }

    function addManageFee() {
        d = $("#dlg").dialog({
            title: '计算管理费',
            width: 380,
            height: 380,
            href: '${ctx}/system/interface/managefee/invoke',
            maximizable: true,
            modal: true,

            buttons: [{
                text: '确认',
                handler: function () {
                    $("#mainform").submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }

    function addFeeOverview() {
        d = $("#dlg").dialog({
            title: '计算费用总览',
            width: 380,
            height: 380,
            href: '${ctx}/system/interface/overview/invoke',
            maximizable: true,
            modal: true,

            buttons: [{
                text: '确认',
                handler: function () {
                    $("#mainform").submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }

</script>
</body>
</html>