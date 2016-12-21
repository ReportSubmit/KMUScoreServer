<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/tags.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet" href="/score/resources/css/table.css">
<link rel="stylesheet" href="/score/resources/css/button.css">
<style type="text/css">
.bs-callout {
	padding: 20px;
	margin: 20px 0;
	border: 1px solid #eee;
	border-left-width: 5px;
	border-radius: 3px;
}

.bs-callout h4 {
	margin-top: 0;
	margin-bottom: 5px;
}

.bs-callout p:last-child {
	margin-bottom: 0;
}

.bs-callout code {
	border-radius: 3px;
}

.bs-callout+.bs-callout {
	margin-top: -5px;
}

.bs-callout-default {
	border-left-color: #777;
}

.bs-callout-default h4 {
	color: #777;
}

.bs-callout-primary {
	border-left-color: #428bca;
}

.bs-callout-primary h4 {
	color: #428bca;
}

.bs-callout-success {
	border-left-color: #5cb85c;
}

.bs-callout-success h4 {
	color: #5cb85c;
}

.bs-callout-danger {
	border-left-color: #d9534f;
}

.bs-callout-danger h4 {
	color: #d9534f;
}

.bs-callout-warning {
	border-left-color: #f0ad4e;
}

.bs-callout-warning h4 {
	color: #f0ad4e;
}

.bs-callout-info {
	border-left-color: #5bc0de;
}

.bs-callout-info h4 {
	color: #5bc0de;
}
</style>

</head>
<body>
	<%@include file="../include/header.jsp"%>
	<div class="container">
		<div class="row">
			<div class="col-md-9">
			<form action="<c:url value="/admin/export/export_file/excel"/>" method="get">
				<div class="bs-callout bs-callout-danger">
					<h4>과목 선택</h4>
					<select class="form-control" name="projectIdx">
						<c:forEach items="${projects}" var="project">
							<option value="${project.projectIdx}">${project.projectName}</option>
						</c:forEach>
					</select>
				</div>
				<div class="bs-callout bs-callout-warning">
					<h4>출력 폼 선택</h4>
					<div class="ui big buttons">
						<button id="excelBtn" type="submit"
							class="ui inverted green button">엑셀다운</button>
					</div>
				</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>