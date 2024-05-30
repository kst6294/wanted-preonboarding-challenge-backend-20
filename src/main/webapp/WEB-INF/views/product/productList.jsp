<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<link href="/resources/css/board.css" rel="stylesheet" />
<section class="notice">
  <div class="page-title">
        <div class="container">
            <h3>물품 목록</h3>
        </div>
    </div>

    <!-- board search area -->
    <div id="board-search">
        <div class="container">
            <div class="search-window">
                <form action="">
                    <div class="search-wrap">
                        <label for="search" class="blind">공지사항 내용 검색</label>
                        <input id="search" type="search" name="" placeholder="검색어를 입력해주세요." value="">
                        <button type="submit" class="btn btn-dark">검색</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

  <!-- board list area -->
    <div id="board-list">
        <div class="container">
            <table class="board-table">
                <thead>
                			<tr>
                				<th>번호</th>
                				<th>상태</th>
                				<th>판매자</th>
                				<th>판매물건</th>
                				<th>설명</th>
                				<th>가격</th>
                			</tr>
                		</thead>
                		<c:forEach items="${lists.content}" var="product">
                			<tr>
                				<td>${product.p_id}</td>
                				<td><a href="/product/productDetail/${product.p_id}">${product.state}</td>
                				<td>${product.seller}</td>
                				<td>${product.name}</td>
                				<td>${product.description}</td>
                				<td>${product.price}</td>
                			</tr>
                		</c:forEach>
            </table>
        </div>
    </div>

</section>