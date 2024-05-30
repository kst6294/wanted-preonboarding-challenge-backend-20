<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link href="../resources/css/index.css" rel="stylesheet" />
<!DOCTYPE html>
<body>
        <div class="container">
            <div class="login form">
             <header>판매 등록</header>
             <form action="productInsert" method="POST">
             <input type="text" class="form__input" id="name" name="name" placeholder="제품명">
             <input type="text" class="form-control" id="seller" name="seller" readonly="readonly" value="${principal.user.name }">
             <input type="text" class="form__input" id="price" name="price" placeholder="가격">
             <input type="text" rows="5" name="description" id="description" placeholder="설명">
             <button type="submit" id="insert" class="button"> 등록하기 </button>
             </form>
            </div>
    </div>
</body>