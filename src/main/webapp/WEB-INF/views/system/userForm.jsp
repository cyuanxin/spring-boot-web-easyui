<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx }/system/user/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>用户名：</td>
				<td>
					<input type="hidden" name="id" value="${id}"/>
					<input id="loginName" name="loginName" class="easyui-validatebox" data-options="width: 150, invalidMessage:'长度不在2-20位或者用户名已存在'" value="${user.loginName}">
				</td>
			</tr>
            <c:if test="${action != 'update'}">
			<tr>
				<td>密码：</td>
				<td><input id="plainPassword" name="plainPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'Password'"/></td>
			</tr>
			<tr>
				<td>确认密码：</td>
				<td><input id="confirmPassword" name="confirmPassword" type="password" class="easyui-validatebox" data-options="width: 150,required:'required',invalidMessage:'两次输入的密码不一致',validType:'equals[$(\'#plainPassword\').val()]'"/></td>
			</tr>
            </c:if>
			<tr>
				<td>姓名：</td>
				<td><input name="name" type="text" value="${user.name}" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[2,20]'"/></td>
			</tr>
			<tr>
				<td>Email：</td>
				<td><input type="text" name="email" value="${user.email}" class="easyui-validatebox" data-options="width: 150,validType:'email'"/></td>
			</tr>
			<tr>
				<td>电话：</td>
				<td><input type="text" name="phone" value="${user.phone}" class="easyui-validatebox"  data-options="width: 150, validType:'PhoneNumber'"/></td>
			</tr>
            <tr>
                <td>状态：</td>
                <td>
                    <select name="state">
                        　<option value="0" selected="selected">禁用</option>
                        　<option value="1">启用</option>
                    </select>
                </td>
            </tr>


		</table>
	</form>
</div>

<script type="text/javascript">
    $.extend($.fn.validatebox.defaults.rules, {
        Password: {
            validator: function (value) {
                var reg =/^(?![^a-zA-Z]+$)(?!\D+$).{6,20}$/;
                return reg.test(value);
            },
            message: '密码为6-20位数字+字母组合'
        },
		PhoneNumber: {
			validator: function (value) {
				var reg =/^\d+$/;
				return reg.test(value);
			},
			message: '手机号码必须是数字'
		},
    });


    var action="${action}";


//用户 添加修改区分
if(action=='create'){


	//用户名存在验证
	$('#loginName').validatebox({    
	    required: true,    
	    validType:{
	    	length:[2,20],
	    	remote:["${ctx}/system/user/checkLoginName","loginName"]
	    }
	});



}else if(action=='update'){
	$("input[name='loginName']").attr('readonly','readonly');
	$("input[name='loginName']").css('background','#eee');




}

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