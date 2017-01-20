<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<%@ include file="/WEB-INF/views/include/easyui.jsp"%>

</head>
<body>
<div>
	<form id="mainform" action="${ctx}/merchant/info/${action}" method="post">
		<table class="formTable">
			<tr>
				<td>商户编号：</td>
				<td>
					<input type="hidden" name="id" value="${m.id}"/>
					<input id="merchantId" name="merchantId" class="easyui-validatebox" data-options="width: 150, invalidMessage:'商户编号已存在'" value="${m.merchantId}">
				</td>
			</tr>
			<tr>
				<td>商户名字：</td>
				<td><input name="name" type="text" value="${m.name}" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[2,20]'"/></td>
			</tr>
			<tr>
				<td>联系人：</td>
				<td><input name="contacts" type="text" value="${m.contacts}" class="easyui-validatebox" data-options="width: 150,required:'required',validType:'length[2,20]'"/></td>
			</tr>
			<tr>
				<td>联系邮箱：</td>
				<td><input type="text" name="email" value="${m.email}" class="easyui-validatebox" data-options="width: 150,validType:'email', required:'required'"/></td>
			</tr>
			<tr>
				<td>联系电话：</td>
				<td><input type="text" name="phone" value="${m.phone}" class="easyui-numberbox"  data-options="width: 150, required:'required'"/></td>
			</tr>
            <tr>
                <td>状态：</td>
                <td>
                    <select name="status">
                        　<option value="0" selected="selected">禁用</option>
                        　<option value="1">启用</option>
                    </select>
                </td>
            </tr>
			<tr>
				<td>合作业务：</td>
				<td>
					<select id ="busType" name="busType" >
						　<option value="-1">未选择</option>
						　<option value="0">基金业务</option>
					</select>
				</td>
			</tr>

		</table>

		<table id="cortable" class="formTable" style="display:none">
			<tr>
				<td>合作方式：</td>
				<td>
					<select id="accessType" name="accessType">
						　<option value="0">sdk</option>
						　<option value="1">open api</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>是否签订合同：</td>
				<td>
					<select id="isSigned" name="isSigned">
						　<option value="0">否</option>
						　<option value="1">是</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>合同到期日：</td>
				<td>
				    <input id="contractDueDate" name="contractDueDate" type="text" class="datebox" datefmt="yyyy-MM-dd" data-options="width: 150" value="<fmt:formatDate value="${m.contractDueDate}"/>"/>
				</td>
			</tr>
			<tr>
				<td>货基比例(%)：</td>
				<td> <input id="mPercentage"  name="mPercentage" value="${m.mPercentage}" class="easyui-numberbox" data-options="width: 150,  validType:'Rate', precision:2"/> </td>
			</tr>
			<tr>
				<td>股基比例(%)：</td>
				<td> <input id="sPercentage" name="sPercentage"  value="${m.sPercentage}" class="easyui-numberbox" data-options="width: 150,  validType:'Rate', precision:2"/> </td>
			</tr>

		</table>
	</form>
</div>

<script type="text/javascript">

    var action="${action}";

	$.extend($.fn.numberbox.defaults.rules, {
		Rate: {
			validator: function (value) {
				if (value > 100 || value <= 0) {
					return false
				}

				return true;
			},
			message: '分成比例在0-100之间'
		},
	});

    function changeCorpTable(val) {
        if (val == 0 ) {
            $("#cortable").css('display','block')

            $("#mPercentage").validatebox({
                required: true,
            });
            $("#sPercentage").validatebox({
                required: true,
            });

		    var isSign = $("#isSigned").val()
			if (isSign == 1) {
				$("#contractDueDate").datebox({
					required: true,
				});
			} else {
				$("#contractDueDate").datebox({
					required: false,
				});
			}

        } else {
            $("#cortable").css('display','none')

            $("#mPercentage").validatebox({
                required: false,
            });
            $("#sPercentage").validatebox({
                required: false,
            });
			$("#contractDueDate").datebox({
				required: false,
			});

        }

    }

    $("#busType").change(function (e) {
        var val = $(e.target).val();
        changeCorpTable(val);
    });

	$("#isSigned").change(function (e) {
		var val = $(e.target).val();
		if (val == 1) {
			$("#contractDueDate").datebox({
				required: true,
			});
		} else {
			$("#contractDueDate").datebox({
				required: false,
			});
		}
	});


//用户 添加修改区分
if(action=='create'){

	//用户名存在验证
	$('#merchantId').validatebox({
	    required: true,    
	    validType:{
	    	length:[2,20],
	    	remote:["${ctx}/merchant/info/checkMerchantId","merchantId"]
	    }
	});



}else if(action=='update'){
	$("input[name='merchantId']").attr('readonly','readonly');
	$("input[name='merchantId']").css('background','#eee')


	$("#busType").val("${m.busType}")
	$("#accessType").val("${m.accessType}")
    $("#isSigned").val("${m.isSigned}")
    changeCorpTable("${m.busType}")


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