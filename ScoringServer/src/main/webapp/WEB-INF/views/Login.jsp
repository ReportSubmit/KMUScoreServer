<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function doLogin() {

		if (loginForm.username.value == "") {
			alert("아이디를 입력해주세요.");
			return;
		}
		if (loginForm.password.value == "") {
			alert("비밀번호를 입력해주세요.");
			return;
		}

		loginForm.submit();
	}
</script>
</head>
<body>
	<form name="loginForm" action="j_spring_security_check" method="post">
		<input type="text" name="username" placeholder="아이디"> <input
			type="password" name="password" placeholder="비밀번호">
		<sec:csrfInput />
		<input type="submit" value="로그인" onclick="doLogin()">
	</form>
</body>
</html>