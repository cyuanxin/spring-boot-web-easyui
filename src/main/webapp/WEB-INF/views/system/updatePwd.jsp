<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>
<script type="text/javascript" src="${ctx}/static/plugins/jquery/jquery.form.js"></script>
</head>
<body>
	<div style="padding: 5px">
	<form id="mainform" action="${ctx}/system/user/updatePwd" method="post">
	<table>
		<tr>
			<td>密码：</td>
			<td>
				<input type="hidden" name="id" value="${user.id}"/>
				<input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'Password'"/>
			</td>
		</tr>
		<tr>
			<td>确认密码：</td>
			<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',invalidMessage:'两次输入的密码不一致',validType:'equals[$(\'#plainPassword\').val()]'"/></td>
		</tr>
	</table>
	</form>
</div>
<script>
	$.extend($.fn.validatebox.defaults.rules, {
		Password: {
			validator: function (value) {
				var reg =/^(?![^a-zA-Z]+$)(?!\D+$).{6,20}$/;
				return reg.test(value);
			},
			message: '密码为6-20位数字+字母组合'
		},
	});

	//提交表单
	$('#mainform').form({
		onSubmit: function(){
			var isValid = $(this).form('validate');
			return isValid;	// 返回false终止表单提交
		},
		success:function(data){
			successTip(data,dg,d);
		}
	});
</script>
</body>
</html>