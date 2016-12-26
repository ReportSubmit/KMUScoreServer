<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style>
.bs-docs-nav .navbar-toggle .icon-bar {
    background-color: #563d7c;
}
.navbar-toggle .icon-bar {
    display: block;
    width: 22px;
    height: 2px;
    border-radius: 1px;
}
</style>

<script type='text/javascript'>
function submit()
 {
    $('#logoutForm').submit();
 }
</script>

<header class="navbar navbar-static-top bs-docs-nav" id="top"
	role="banner">
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="navbar-header">
					<button class="navbar-toggle collapsed" type="button"
						data-toggle="collapse" data-target=".bs-navbar-collapse">
						<span class="sr-only">네비게이션 끄기/켜기</span> <span class="icon-bar"></span>
						<span class="icon-bar"></span> <span class="icon-bar"></span>
					</button>
					<a href="<c:url value="/main"/>" class="navbar-brand">홈</a>
				</div>
				<nav class="collapse navbar-collapse bs-navbar-collapse">
					<ul class="nav navbar-nav">
						<li><a href="<c:url value="/getting-started"/>">시작하기</a></li>

						<li><a href="<c:url value="/read/problems"/>">문제풀이</a></li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value="/admin/upload/problem"/>">문제업로드</a></li>
							<li><a href="<c:url value="/admin/choose/export_file"/>">파일출력</a></li>
						</sec:authorize>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li><a href="javascript: submit()">로그아웃</a></li>
					</ul>

				</nav>
				<form id="logoutForm" action="<c:url value="/logout"/>" method="post">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</div>
		</div>
	</div>

</header>
