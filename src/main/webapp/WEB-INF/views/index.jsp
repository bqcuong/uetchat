<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>It works</title>
</head>
<body>
<h1>It works</h1>
<code><em>UET Stranger Chat App</em></code>

<br/>

<c:if test="${not empty message}">${message}</c:if>

<br/>

<c:if test="${not empty users}">
	<c:forEach var="u" items="${users}">
		${u} <br/>
	</c:forEach>
</c:if>

</body>
</html>