<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ITSP 채점 서버</title>
</head>
<body>
	<header>
		<table>
			<tr>
				<td><a href="./main">홈</a></td>
				<td><a href="./student/prob_board">문제 풀이</a></td>
				<td>자유 게시판(준비중)</td>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<td><a href="./admin/upload">문제 업로드</a></td>
					<td><a href="./admin/score_board">학생 점수 확인</a></td>
				</sec:authorize>
				<td><sec:authorize
						access="hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')">
						<sec:authentication property="name" />
						<a href="./logout">Logout</a>
					</sec:authorize></td>
			</tr>
		</table>
	</header>
	<section></section>
	<footer></footer>
</body>
</html>