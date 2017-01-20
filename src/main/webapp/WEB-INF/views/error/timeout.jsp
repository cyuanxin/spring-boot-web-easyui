<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%response.setStatus(200);%>

<!DOCTYPE html>
<html>
<head>
	<title>登录超时</title>
</head>

<body>
	<h2>登录超时，请重新登录</h2>
</body>
<script>
	//全局的AJAX访问，处理AJAX清求时SESSION超时
	window.parent.location.href = '${ctx}/a/login';
</script>
</html>