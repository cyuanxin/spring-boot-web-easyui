<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/system/interface/${module}/invoke" method="post">
		<table class="formTable">
			<tr>
				<td>开始日期：</td>
				<td>
					<input id="startDate" name="startDate" type="text" class="easyui-my97" datefmt="yyyyMMdd" data-options="width: 150, required:'required'" />
				</td>
			</tr>
			<tr>
				<td>结束日期：</td>
				<td>
					<input id="endDate" name="endDate" type="text" class="easyui-my97" datefmt="yyyyMMdd" data-options="width: 150, required:'required'" />
				</td>
			</tr>

		</table>
	</form>

</div>

<script type="text/javascript">

//提交表单
$('#mainform').form({    
    onSubmit: function(){    
    	var isValid = $(this).form('validate');
		return isValid;	// 返回false终止表单提交
    },
    success:function(data) {
		if(data=='success'){
			if(d!=null)
				d.panel('close');
			parent.$.messager.show({ title : "提示",msg: "操作成功！", position: "bottomRight" });
			return true;
		}else{
			parent.$.messager.alert(data);
			return false;
		}
	}
});    
</script>
</body>
</html>