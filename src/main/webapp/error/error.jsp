<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="http://localhost:8080/yellowAsian/">
<title>Error</title>
</head>
<body>
	<h1>Access Denied Page</h1>
	
	<h2>
		<c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage() }" />
	</h2>
	<h2>
		<c:out value="${msg}" />
	</h2>
	
	<div class="buttonGroup">
         <input type="submit" value="goBack" class="goBackButton">
         <button class="registerButton" type="button" onclick="location.href='user/welcomePage.do'">Go Back</button>
     </div>
</body>
</html>