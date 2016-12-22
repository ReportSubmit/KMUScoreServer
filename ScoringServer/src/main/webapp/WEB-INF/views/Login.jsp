<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="./include/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
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

	<div class="col-md-offset-4 col-md-4" style="margin-top: 30px;">
		<div class="panel panel-default">
			<div class="panel-body">
				<form name="loginForm" action="j_spring_security_check"
					method="post">
					<sec:csrfInput />
					<div class="form-group">
						<label for="id">아이디</label><input type="text" name="username"
							class="form-control" placeholder="아이디">
					</div>
					<div class="form-group">
						<label for="password">암호</label><input type="password"
							name="password" class="form-control" placeholder="비밀번호">
					</div>
					<button type="submit" class="btn btn-primary" onclick="doLogin()">로그인</button>
					<a class="btn btn-primary" href="#" data-toggle="modal"
						data-target="#signup" style="margin-left: 10px;">회원가입</a>
				</form>
			</div>
		</div>
		<c:if test="${param.msg ne null}">
			<h3><span class="label label-info">${param.msg}</span></h3>
		</c:if>
	</div>

	<div class="modal fade bs-example-modal-sm" id="signup" tabindex="-1"
		role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true">

		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="panel panel-default" style="margin: 0px;">
					<div class="panel-body">
						<form action="./signup" method="post">
							<sec:csrfInput />
							<div class="form-group">
								<input class="form-control" type="text" name="userID"
									placeholder="아이디">
							</div>
							<div class="form-group">
								<input class="form-control" type="password" name="userPwd" placeholder="비밀번호">
							</div>
							<div class="form-group">
								<select class="form-control" name="projectIdx">
									<c:forEach items="${projects}" var="project">
										<option value="${project.projectIdx}">${project.projectName}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<input class="form-control" type="submit" value="등록"> <br/> <input class="form-control" type="reset"
									value="리셋">
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>
	</div>

</body>
</html>