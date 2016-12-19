<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header class="navbar navbar-static-top bs-docs-nav" id="top"
	role="banner">
	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="navbar-header">
					<a href="/score/main" class="navbar-brand">홈</a>
				</div>
				<nav class="collapse navbar-collapse bs-navbar-collapse">
					<ul class="nav navbar-nav">
						<li><a href="/score/getting-started">시작하기</a></li>

						<li><a
							href="/score/read/problems">문제풀이</a></li>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="/score/admin/prob/upload">문제업로드</a></li>
						</sec:authorize>
					</ul>

					<ul class="nav navbar-nav navbar-right">
						<li><a href="/score/logout">로그아웃</a></li>
					</ul>

				</nav>
			</div>
		</div>
	</div>

</header>
