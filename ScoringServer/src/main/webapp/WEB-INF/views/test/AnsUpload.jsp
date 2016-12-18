<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form:form commandName="ScoringInfoBean" action="problem" method="post"
		enctype="multipart/form-data">
		<!--나중에 scoring info bean 이용할 것-->

		<form:select path="complieLanguage" htmlEscape="true">
			<sec:authorize access="hasRole('ROLE_PROG') OR hasRole('ROLE_COMS')">
				<form:option value="101">C</form:option>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_JAVA')">
				<form:option value="102">JAVA</form:option>
			</sec:authorize>
		</form:select>

		<form:select path="project" htmlEscape="true">
			<sec:authorize access="hasRole('ROLE_PROG')">
				<form:option value="program">프로그래밍언어</form:option>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_COMS')">
				<form:option value="coms">전산학실습</form:option>
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_JAVA')">
				<form:option value="java">자바프로그래밍</form:option>
			</sec:authorize>
		</form:select>

		<br />
		문제번호<form:input path="probNo" htmlEscape="true" />
		<br />
		업로드파일<input name="uploadFile" type="file">
		<br />
		<input type="submit" value="확인">
	</form:form>



</body>
</html>