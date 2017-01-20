<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body class="easyui-layout" style="font-family: '微软雅黑'">
<div data-options="region:'west',split:true,border:false,title:'菜单列表'" style="width: 300px">
    <table id="menuDg"></table>
</div>
<div data-options="region:'center',split:true,border:false,title:'权限列表'">
    <shiro:hasRole name="admin">
        <div id="tg_tb" style="padding:5px;height:auto">
            <div>
                <shiro:hasPermission name="sys:perm:add">
                    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="add()">添加</a>
                    <span class="toolbar-item dialog-tool-separator"></span>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:perm:delete">
                    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-remove" onclick="del()">删除</a>
                    <span class="toolbar-item dialog-tool-separator"></span>
                </shiro:hasPermission>
                <shiro:hasPermission name="sys:perm:update">
                    <a href="#" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="upd()">修改</a>
                    <span class="toolbar-item dialog-tool-separator"></span>
                </shiro:hasPermission>
            </div>
        </div>
    </shiro:hasRole>
    <table id="dg"></table>
</div>
<div id="dlg"></div>
<div id="icon_dlg"></div>

<script type="text/javascript">
    var dg;
    var d;
    var menuDg;
    var menuId = 0;
    var parentPermId;
    $(function () {
        menuDg = $('#menuDg').treegrid({
            method: "get",
            url: '${ctx}/system/permission/menu/json',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            treeField: 'name',
            parentField: 'pid',
            iconCls: 'icon',
            animate: true,
            rownumbers: true,
            singleSelect: true,
            striped: true,
            columns: [[
                {field: 'id', title: 'id', hidden: true},
                {field: 'name', title: '名称', width: 100}
            ]],
            enableHeaderClickMenu: false,
            enableHeaderContextMenu: false,
            enableRowContextMenu: false,
            dataPlain: true,
            onClickRow: function (rowData) {
                menuId = rowData.id;
                parentPermId = rowData.id;
                dg.datagrid('reload', {pid: menuId});
            }
        });

        dg = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/system/permission/ope/json',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            treeField: 'name',
            parentField: 'pid',
            iconCls: 'icon',
            animate: true,
            rownumbers: true,
            singleSelect: true,
            striped: true,
            columns: [[
                {field: 'id', title: 'id', hidden: true, width: 100},
                {field: 'name', title: '名称', width: 100},
                {field: 'url', title: '访问路径', width: 100},
                {field: 'permCode', title: '权限编码', width: 100},
                {field: 'sort', title: '排序'},
                {field: 'description', title: '描述', width: 100}
            ]],
            toolbar: '#tg_tb',
            dataPlain: true
        });

    });

    //弹窗增加
    function add() {
        d = $('#dlg').dialog({
            title: '添加权限',
            width: 450,
            height: 300,
            closed: false,
            cache: false,
            maximizable: true,
            resizable: true,
            href: '${ctx}/system/permission/create',
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

    //删除
    function del() {
        var row = dg.datagrid('getSelected');
        if (rowIsNull(row)) return;
        parent.$.messager.confirm('提示', '删除后无法恢复您确定要删除？', function (data) {
            if (data) {
                $.ajax({
                    type: 'get',
                    url: "${ctx}/system/permission/delete/" + row.id,
                    success: function (data) {
                        successTip(data, dg);
                    }
                });
                //dg.datagrid('reload'); //grid移除一行,不需要再刷新
            }
        });

    }

    //修改
    function upd() {
        var row = dg.datagrid('getSelected');
        if (rowIsNull(row)) return;
        d = $("#dlg").dialog({
            title: '修改权限',
            width: 450,
            height: 300,
            href: '${ctx}/system/permission/update/' + row.id,
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

    //快速添加基本操作
    function addBase() {
        /* $.ajax({
         type:'get',
         url:"
        ${ctx}/system/permission/createBase/"+parentPermId,
         success: function(data){
         if(data=='success'){
         dg.datagrid('reload');
         parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
         } else{
         parent.$.messager.alert('提示',data);
         }
         }
         }); */
    }
</script>
</body>
</html>