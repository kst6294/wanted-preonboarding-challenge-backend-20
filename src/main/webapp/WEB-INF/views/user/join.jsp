<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp"%>
<!DOCTYPE html>
<link href="resources/css/form.css" rel="stylesheet" />
<title>wanted</title>
<body>
    <div class="container">
        <header>회원 가입</header>
            <section>
                <div class="fields">
                <div class="input-field">
                <label>Role</label>
                <input type="text" class="form-check-input"name="role" value="ROLE_USER" id="role" readonly="readonly"/>
                </div>
                <div class="input-field">
                <label>Name</label>
                <input type="text" id = "name" name = "name" placeholder="Name" />
                </div>
                <div class="input-field">
                <label>Email</label>
                <input type="email" id = "email" name = "email" placeholder="Email" />
                </div>
                <div class="input-field">
                <label>Password</label>
                <input type="password" id = "password" name = "password" placeholder="Password" />
                </div>
              <button class="nextBtn" id="btnJoin">Sign Up</button>
        </section>
    </div>
</body>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	$("#btnJoin")
			.click(
					function() {
						var regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/
						var pw = $("#password").val();
						if ($("#email").val() == "") {
							alert("아이디를 입력하세요")
							$("#email").focus();
							return false;
						}
						if (!$("#email").val().match(regEmail)) {
							alert("이메일 양식이 아닙니다");
							$("#email").focus();
							return false;
						}
						if ($("#password").val() == "") {
							alert("비밀번호를 입력하세요")
							$("#password").focus();
							return false;
						}

						if (pw.length < 8) {
							alert("비밀번호는 최소 8자리 입니다.")
							$("password").focus();
							return false;
						}

						var dataParam = {
							"name" : $("#name").val(),
							"email" : $("#email").val(),
							"password" : $("#password").val()
						}
						let param =JSON.stringify(dataParam);
						$.ajax({
                                		type : "POST",
                                		url : "/register",
                                		contentType : "application/json;charset=utf-8",
                                		data : JSON.stringify(dataParam)
                                	})
                                .done(function(resp) {
                                		if (resp == "success") {
                                			alert("회원 가입 성공");
                                			location.href = "/login";
                                		} else if (resp == "fail") {
                                			alert("아이디 중복");
                                			$("#useremail").val("");
                                		}
                                	}).fail(function() {
                                		alert("회원가입실패");
                                	})//fail
                                })//btn
</script>