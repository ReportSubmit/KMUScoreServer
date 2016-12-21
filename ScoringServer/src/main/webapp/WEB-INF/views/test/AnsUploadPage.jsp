<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	function ajaxScoreFile() {

		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		var probNum = $("#prob_select option:selected").val();
		var uploadFile = $("input[name=uploadFile]")[0].files[0];
		//폼객체를 불러와서
		var form = $('form')[0];
		//FormData parameter에 담아줌
		var formData = new FormData(form);
		var probList = [];
		//첫번째 파일태그
		formData.append("uploadFile", uploadFile);
		formData.append("probNum", probNum);

		$.ajax({
			type : "POST",
			data : formData,
			processData : false,
			contentType : false,
			url : "./ajax/C/scoring/file",
			beforeSend : function(xhr) {
				// here it is
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success : function(obj) {

				$("#code_success").text(
						textToHtmlAboutNewLineAndTab(obj.successMsg));
				$("#code_error").html(textToHtmlAboutNewLineAndTab(obj.errorMsg));
				$("#code_msg").text(textToHtmlAboutNewLineAndTab(obj.message));
				$("#code_score").text("점수: "+obj.score);

				probList = obj.probList;

				$("#code_result tr").remove();
				$("#code_result").append("<tr><th>Test</th><th>입력</th><th>정답</th><th>비고</th></tr>");
				$.each(probList, function(index, prob) {
					var appendValue = "<td>"+(index+1)+"</td><td>"+prob.input+"</td><td>"+prob.answerList;
				
					if(prob.isRight === 1){
						appendValue="<tr class='success'>"+appendValue + "<td></td>";
					}else{
						appendValue="<tr class='danger'>"+appendValue + "<td>오답</td>";
					}
					
					appendValue= appendValue+"</tr>"
					$("#code_result").append(appendValue);
				});

			},
			complete : function(data) {// 응답이 종료되면 실행, 잘 사용하지않는다

			},
			error : function(xhr, status, error) {
				alert("fail");
			}
		});
	};
	function textToHtmlAboutNewLineAndTab(str) {
		var covertStr = str.replace(/\r\n|\r|\n/g, '<br />');
		return covertStr.replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
	};
</script>

</head>
<!-- /ajax/C/scoring/file  -->

<body
	style="background-image: url('./resources/images/image/kakao_backgroud.PNG'); background-attachment: fixed;">
	&nbsp;
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-4">
				<select class="form-control" id="prob_select">
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
				<input class="btn btn-lg btn-success" type="button" value="정답 테스트"
					onclick=""> <input class="btn btn-lg btn-warning"
					type="hidden" value="최종 제출" onclick="">
			</div>
		</div>
		&nbsp;
		<div class="row">
			<div class="col-md-6">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">코드결과</h3>
					</div>
					<div class="panel-body">
						<div id="code_msg"></div>
						<div id="code_success"></div>
						<div id="code_error"></div>
						<div id="code_score"></div>
						<div class="table-responsive">
							<table id="code_result" class="table table-condensed">
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="col-sm-2">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">파일 업로드</h3>
					</div>
					<div class="panel-body">
						<div id="upload_file">
							<input type="file" name="uploadFile">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-8">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">주의 사항</h3>
					</div>
					<div class="panel-body">
						<div></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>