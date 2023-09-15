<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>Member Login Form</h3>
	
	<form action="<c:url value='/member/loginConfirm'/>" method="post">
		<input type="text" name="m_id" /> <br />
		<input type="password" name="m_pw" /> <br />
		<input type="submit" value="Login" />
		<input type="reset" value="reset" />
	</form>
</body>
</html>