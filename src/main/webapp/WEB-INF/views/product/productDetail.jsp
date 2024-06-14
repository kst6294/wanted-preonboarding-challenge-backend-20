<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<link href="../../resources/css/form.css" rel="stylesheet" />
<!DOCTYPE html>
<body>
   <div class="container">
    <header>물품 상세</header>
    <section>
        <div class="section first">
        <div class="details personal">
        <span class="title">물품 정보</span>
        <div class="fields">
            <div class="input-field">
            <label>물품명</label>
            <input type="text" id="name" name="name" value="${product.name }" readonly="readonly">
            </div>
            <div class="input-field">
            <label>판매자</label>
            <input type="text" id="seller" name="seller" value="${product.seller }" readonly="readonly">
            </div>
            <div class="input-field">
            <label>상태</label>
            <input type="text" id="p_state" name="p_state" value="${product.p_state }" readonly="readonly">
            </div>
            <div class="input-field">
            <label>가격</label>
            <input type="text" id="price" name="price" value="${product.price }" readonly="readonly">
            </div>
            <div class="input-field">
            <label>설명 </label>
            <input type="text" id="description" name="description" readonly="readonly" value="${product.description }">
            </div>
            <div class="input-field">
            <label hidden>글번호</label>
            <input type="hidden" id="p_id" name="p_id" value="${product.p_id }" readonly="readonly">
            </div>
        </div>
        </div>
    <div class="details ID">
    <span></span>
    <c:if test="${principal.user.name != product.seller and product.p_state eq 'ONSALE'}">
            <input type="hidden" id="u_id" value="${product.user.u_id}" >
            <button type="button" class="btn btn-danger btn-sm" id="btnOrder">구매하기</button>
    </c:if>
    <div>
    <c:forEach var ="tr" items="${sell}" varStatus="status">
        <c:if test ="${principal.user.u_id eq tr.user.u_id}">
                        <p>${tr.user.name}님 거래현황</p>
                        <p>${tr.t_state}</p>
        </c:if>
    </c:forEach>
    <c:if test="${principal.user.name == product.seller}">
        <c:forEach var ="tr" items="${sell}" varStatus="status">

            <c:if test="${tr.t_state eq 'RESERVED'}">
                        <tr>
                            <td> ${tr.user.name}  </td><br/>
                            <button type="button" class="btnAccept"  id="transaction${tr.t_id}">거래 승인</button>
                            <input type="text" value="${tr.t_id}" name="transacta sdion2" id="transaction2">
                        </tr>
            </c:if>
        </c:forEach>
    </c:if>

        </div>
    <div>
    </div>
    </div>
</section>
</div>
</body>

<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
    //판매자 거래 승인 var dd = $(this).next().val();
    $('.btnAccept').click(function() {
        data = {
            product : { "p_id" : $("#p_id").val() },
            transaction : { "t_id" : $(this).next().val() }}
        console.log(data);
        $.ajax({
            type : "put",
            url : "/product/update",
            contentType : "application/json;charset=utf-8",
            data : JSON.stringify(data),
            success : function(resp) {
                if (resp == "success") {
                    alert("거래 승인 성공")
                    location.href = "/product/productList";
                }
            },
            error : function(e) {
                alert("거래 승인 실패 : " + e);
            }
            })
        })//btnComplete
        </script>
<script>
        $("#btnOrder").click(function() {
            if(${empty principal.user}){
                alert("로그인하세요")
                location.href="/login"
                return
            }
            var num =$("#u_id").val();

            $.ajax({
                type : "post",
                url : "/transaction/insert/${product.p_id }",
                data : {u_id:num},

            }).done(function() {
               alert("예약 성공");
               location.href = "/product/productList";
            }).fail(function() {
                alert("예약 실패")
            })
    })//btnOrder
</script>