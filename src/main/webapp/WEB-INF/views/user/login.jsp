<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<link href="resources/css/index.css" rel="stylesheet" />
<body>
    <c:if test="${not empty errorMsg }"> ${errorMsg} </c:if>
<div class="container">
    <div class="login form">
        <header>Login</header>
        <form action="/login" method="post">
            <input type="email" id = "email" name ="email" placeholder="Email" />
            <input type="password" id = "password" name="password" placeholder="Password" />
            <button class="button">Sign In</button>
    </form>
 </div>
</body>