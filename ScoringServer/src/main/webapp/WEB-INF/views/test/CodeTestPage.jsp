<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<sec:csrfMetaTags />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
	function ajaxCheckCode() {

		//ajaxCheckAnswer();

		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		var textCode = $("#code").val();
		var input = $("#input").val();

		$.ajax({
			type : "POST",
			url : "./ajax/C/test",
			data : {
				"code" : textCode,
				"input" : input
			},
			datatype : "JSON",
			beforeSend : function(xhr) {
				// here it is
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success : function(obj) {
				$("#code_success").html(textToHtmlAboutNewLineAndTab(obj.successMsg));
				$("#code_error").html(textToHtmlAboutNewLineAndTab(obj.errorMsg));
				$("#code_msg").html(textToHtmlAboutNewLineAndTab(obj.message));
			},
			complete : function(data) {// 응답이 종료되면 실행, 잘 사용하지않는다

			},
			error : function(xhr, status, error) {

			}
		});
	};
	function ajaxCheckAnswer() {

		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		var fileName = $("#file_select option:selected").val();
		var input = $("#input").val();

		$.ajax({
			type : "POST",
			url : "./ajax/C/answer",
			data : {
				"fileName" : fileName,
				"input" : input
			},
			datatype : "JSON",
			beforeSend : function(xhr) {
				// here it is
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success : function(obj) {
				$("#ans_success").html(textToHtmlAboutNewLineAndTab(obj.successMsg));
				$("#ans_error").html(textToHtmlAboutNewLineAndTab(obj.errorMsg));
				$("#ans_msg").html(textToHtmlAboutNewLineAndTab(obj.message));
			},
			complete : function(data) {// 응답이 종료되면 실행, 잘 사용하지않는다

			},
			error : function(xhr, status, error) {

			}
		});
	};
	function textToHtmlAboutNewLineAndTab(str){
		var covertStr =str.replace(/\r\n|\r|\n/g, '<br />'); 
		return covertStr.replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
	};
</script>

<title>정답확인 페이지</title>

</head>
<body
	style="background-image: url('./resources/images/image/kakao_backgroud.PNG'); background-attachment: fixed;">
	&nbsp;
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-4">
				<select class="form-control" id="file_select">
					<c:forEach begin="0" items="${fnames}" step="1" var="name"
						varStatus="status">
						<c:choose>
							<c:when test="${status.index == 0}">
								<option selected="selected" value="${name}">${name}</option>
							</c:when>
							<c:otherwise>
								<option value="${name}">${name}</option>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</select>
			</div>
			<div class="col-sm-4">
				<input class="btn btn-lg btn-success" type="button" value="코드 결과 확인"
					onclick="ajaxCheckCode()"> <input
					class="btn btn-lg btn-warning" type="button" value="정답 결과 확인"
					onclick="ajaxCheckAnswer()">
			</div>
		</div>
		&nbsp;
		<div class="row">
			<div class="col-sm-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h3 class="panel-title">코드를 입력하세요</h3>
					</div>
					<div class="panel-body">
						<textarea id="code" class="form-control" rows="30"
							spellcheck="false"></textarea>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h3 class="panel-title">입력 값을 넣어주세요</h3>
					</div>
					<div class="panel-body">
						<textarea id="input" class="form-control" rows="30"
							spellcheck="false"></textarea>
					</div>
				</div>
			</div>

			<div class="col-sm-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">코드결과</h3>
					</div>
					<div class="panel-body">
						<div id="code_msg"></div>
						<div id="code_success"></div>
						<div id="code_error"></div>
					</div>
				</div>
			</div>
			<div class="col-sm-4">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">정답 결과</h3>
					</div>
					<div class="panel-body">
						<div id="ans_msg"></div>
						<div id="ans_success"></div>
						<div id="ans_error"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
