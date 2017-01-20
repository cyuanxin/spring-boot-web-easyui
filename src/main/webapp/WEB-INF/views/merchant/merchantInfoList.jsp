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
            <a href="javascript(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="cx()">查询</a>
        </form>


        <shiro:hasPermission name="merchant:info:add">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
               onclick="add();">添加</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        </shiro:hasPermission>
        <shiro:hasPermission name="merchant:info:update">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
               onclick="upd()">修改</a>
            <span class="toolbar-item dialog-tool-separator"></span>
        </shiro:hasPermission>
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
            url: '${ctx}/merchant/info/json',
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
                {field: 'name', title: '商户名', sortable: true, width: 100},
                {
                    field: 'status', title: '状态', sortable: true,
                    formatter: function (value, row, index) {
                        return value == 0 ? '禁用' : '启用';
                    }
                },
                {
                    field: 'busType', title: '合作业务', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '基金业务'
                        } else {
                            return '未选择'
                        }
                    }
                },
                {
                    field: 'accessType', title: '合作方式', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return 'sdk'
                        } else if (value == 1) {
                            return 'open api'
                        }
                    }
                },
                {field: 'contacts', title: '联系人', sortable: true, width: 100},
                {field: 'email', title: '邮箱', sortable: true, width: 100},
                {field: 'phone', title: '电话', sortable: true, width: 100},
                {field: 'mPercentage', title: '货基分成比例', sortable: true, width: 100},
                {field: 'sPercentage', title: '非货基分成比例', sortable: true, width: 100},
                {field: 'contractDueDate', title: '合同到期日', sortable: true, width: 100, formatter: formatDate},
                {field: 'createDate', title: '创建时间', sortable: true, width: 100, formatter: formatDate},
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

    //弹窗增加
    function add() {
        d = $("#dlg").dialog({
            title: '添加商户',
            width: 380,
            height: 380,
            href: '${ctx}/merchant/info/create',
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


    //弹窗修改
    function upd() {
        var row = dg.datagrid('getSelected');
        if (rowIsNull(row)) return;
        d = $("#dlg").dialog({
            title: '修改商户',
            width: 380,
            height: 340,
            href: '${ctx}/merchant/info/update/' + row.id,
            maximizable: true,
            modal: true,
            buttons: [{
                text: '修改',
                handler: function () {
                    $('#mainform').submit();
                }
            }, {
                text: '取消',
                handler: function () {
                    d.panel('close');
                }
            }]
        });
    }


    function cx() {

        var isValid = $('#merchantNameFilter').validatebox("isValid");
        if (!isValid) {
            return;
        }

        var merchantName = $('#merchantNameFilter').val();
        dg.datagrid('reload', {
            filter_LIKES_merchantName:merchantName
        });
    }

</script>
</body>
</html>