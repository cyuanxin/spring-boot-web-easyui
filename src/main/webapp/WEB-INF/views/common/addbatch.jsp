<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/views/include/easyui.jsp" %>

</head>
<body>
<div>
    <span style="font-size: large"><strong>${msg}</strong></span>
    <br/>
    <form id="mainform" method="post" enctype="multipart/form-data">
        <div>
            <div> 文件:</div>
            <input type="file" name="file">
            <%--<input class="easyui-filebox" name="file" data-options="prompt:'Choose a file...'" style="width:30%">--%>
        </div>
    </form>
</div>
<script type="text/javascript">
    $(function () {
        $('#mainform').form({
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                console.log('data:' + data);
                if(data=='success'){
                    if(dg!=null)
                        dg.datagrid('reload');
                    if(d!=null)
                        d.panel('close');
                    parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
                    return true;
                }else{
                    parent.$.messager.alert(data);
                    return false;
                }
                dg.datagrid('reload');
            }
        });
    });

</script>
</body>
</html>