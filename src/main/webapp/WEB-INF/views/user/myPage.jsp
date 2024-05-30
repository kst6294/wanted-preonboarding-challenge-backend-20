<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<title>마이 페이지</title>

<h2>${principal.user.name}님 거래 목록<h2>
<div class="container">
	<table border="1" style="width:50%; text-align: center;">
    		<thead>판매</thead>
    		<tr>
    			<td>상품명</td>
    			<td>거래상태</td>
    		</tr>
			<c:forEach var ="tr" items="${sell}" varStatus="status">
                <tr>
                    <td> ${tr.product.name}  </td>
                    <td> ${tr.product.state}  </td>
                </tr>
			</c:forEach>
    </table>
	<table border="1" style="width:50%; text-align: center;">
    		<thead>구매</thead>
    		<tr>
    			<td>상품명</td>
    			<td>거래상태</td>
    		</tr>
			<c:forEach var ="tr" items="${buy}" varStatus="status">
                <tr>
                    <td> ${tr.product.name}  </td>
                    <td> ${tr.product.state}  </td>
                </tr>
			</c:forEach>
    </table>
</div>