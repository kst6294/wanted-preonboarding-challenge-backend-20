<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<link href="/resources/css/header.css" rel="stylesheet" />
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<body>
    <sec:authorize access="isAnonymous()">
        <nav class="navbar">
          <div class="navbar__logo">Wanted-Challenge</div>
            <ul>
                <li><a href=" /register">회원가입</a></li>
                <li><a href="/login">로그인</a></li>
                <li><a href=" /product/productList">상품목록</a></li>
            </ul>
        </nav>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
            <nav class="navbar">
                        <c:if test="${principal.role == 'ROLE_USER'}" >
                          <li><a href=" /product/productList">상품목록</a></li>
                        </c:if>
                        <ul>
                          <li><a href="/product/productInsert">판매등록</a></li>
                          <li><a href="/user/myPage/${principal.user.u_id }">${principal.user.name}님 거래목록</a></li>
                          <li><a href="/logout"> 로그아웃 </a></li>
                        </ul>
            </nav>
    </sec:authorize>
<body>