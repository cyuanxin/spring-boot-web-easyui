<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp" %>
</head>
<body class="easyui-layout" style="font-family: '微软雅黑'">
<div data-options="region:'center',split:true,border:false,title:'角色列表'">
    <div id="tb" style="padding:5px;height:auto">
        <div>
            <shiro:hasPermission name="sys:role:add">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="add();">添加</a>
                <span class="toolbar-item dialog-tool-separator"></span>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:role:delete">
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" data-options="disabled:false"
                   onclick="del()">删除</a>
                <span class="toolbar-item dialog-tool-separator"></span>
            </shiro:hasPermission>
            <shiro:hasPermission name="sys:role:update">
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="upd()">修改</a>
            </shiro:hasPermission>
        </div>

    </div>
    <table id="dg"></table>
</div>
<div data-options="region:'east',split:true,border:false,title:'权限列表'" style="width: 425px">

    <div id="tg_tb" style="padding:5px;height:auto">
        <div>
            <shiro:hasRole name="admin">
                <a href="#" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="save();">保存授权</a>
                <span class="toolbar-item dialog-tool-separator"></span>
                <a href="#" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="back()">恢复</a>
            </shiro:hasRole>
        </div>
    </div>

    <table id="permissionDg"></table>
</div>

<div id="dlg"></div>
<script type="text/javascript">
    var dg;	//角色datagrid
    var d; //弹窗
    var permissionDg;	//权限datagrid
    var rolePerData;	//用户拥有的权限
    var roleId;	//双击选中的role
    $(function () {
        dg = $('#dg').datagrid({
            method: "get",
            url: '${ctx}/system/role/json',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            pagination: true,
            rownumbers: true,
            pageNumber: 1,
            pageSize: 10,
            pageList: [10, 20, 30, 40, 50],
            singleSelect: true,
            striped: true,
            columns: [[
                {field: 'id', title: 'id', hidden: true, sortable: true, width: 100},
                {field: 'name', title: '角色名称', sortable: true, width: 100},
                {field: 'roleCode', title: '角色编码', sortable: true, width: 100},
                {field: 'description', title: '描述', sortable: true, width: 100, tooltip: true},
                {
                    field: 'action', title: '操作',
                    formatter: function (value, row, index) {
                        return '<a href="javascript:lookP(' + row.id + ')"><div class="icon-hamburg-lock" style="width:16px;height:16px" title="查看权限"></div></a>';
                    }
                }
            ]],
            enableHeaderClickMenu: false,
            enableHeaderContextMenu: false,
            enableRowContextMenu: false,
            toolbar: '#tb'
        });

        permissionDg = $('#permissionDg').treegrid({
            method: "get",
            url: '${ctx}/system/permission/json',
            fit: true,
            fitColumns: true,
            border: false,
            idField: 'id',
            treeField: 'name',
            parentField: 'pid',
            iconCls: 'icon',
            animate: true,
            rownumbers: true,
            striped: true,
            singleSelect: false,//需设置
            columns: [[
                {field: 'ck', checkbox: true, hidden: true, width: 100},
                {field: 'id', title: 'id', hidden: true, width: 100},
                {field: 'name', title: '名称', width: 100},
                {field: 'description', title: '描述', width: 100, tooltip: true}
            ]],
            onClickRow: function (row) {
                //级联选择
                $(this).treegrid('cascadeCheck', {
                    id: row.id, //节点ID
                    deepCascade: true //深度级联
                });
            },
            toolbar: '#tg_tb'
        });
    });

    //查看权限
    function lookP(roleId) {
        //清空勾选的权限
        if (rolePerData) {
            permissionDg.treegrid('unselectAll');
            rolePerData = [];//清空
        }
        //获取角色拥有权限
        $.ajax({
            async: false,
            type: 'get',
            url: "${ctx}/system/role/" + roleId + "/json",
            success: function (data) {
                if (typeof data == 'object') {
                    rolePerData = data;
                    for (var i = 0, j = data.length; i < j; i++) {
                        permissionDg.treegrid('select', data[i]);
                    }
                } else {
                    $.easyui.messager.alert(data);
                }
            }
        });
    }

    //保存修改权限
    function save() {
        var row = dg.datagrid('getSelected');
        var roleId = row.id;
        parent.$.messager.confirm('提示', '确认要保存修改？', function (data) {
            if (data) {
                var newPermissionList = [];
                var data = permissionDg.treegrid('getSelections');
                for (var i = 0, j = data.length; i < j; i++) {
                    newPermissionList.push(data[i].id);
                }

                if (roleId == null) {
                    parent.$.messager.show({title: "提示", msg: "请选择角色！", position: "bottomRight"});
                    return;
                }
                $.ajax({
                    async: false,
                    type: 'POST',
                    data: JSON.stringify(newPermissionList),
                    contentType: 'application/json;charset=utf-8',
                    url: "${ctx}/system/role/" + roleId + "/updatePermission",
                    success: function (data) {
                        successTip(data);
                    }
                });
            }
        });
    }

    //弹窗增加
    function add() {
        $.ajaxSetup({type: 'GET'});
        d = $('#dlg').dialog({
            title: '添加角色',
            width: 400,
            height: 260,
            closed: false,
            cache: false,
            maximizable: true,
            resizable: true,
            href: '${ctx}/system/role/create',
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
                    url: "${ctx}/system/role/delete/" + row.id,
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
        var rowIndex = row.id;
        $.ajaxSetup({type: 'GET'});
        d = $("#dlg").dialog({
            title: '修改角色',
            width: 400,
            height: 260,
            href: '${ctx}/system/role/update/' + rowIndex,
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

    //恢复权限选择
    function back() {
        var row = dg.datagrid('getSelected');
        lookP(row.id);
    }
</script>
</body>
</html>