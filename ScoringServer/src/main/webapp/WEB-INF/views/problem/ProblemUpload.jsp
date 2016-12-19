<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<sec:csrfMetaTags />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>문제 업로드</title>
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
<link rel="stylesheet" href="/score/resources/css/input.css">
<link rel="stylesheet" href="/score/resources/css/label.css">

<script type="text/javascript">
	$(document).ready(function() {
			
		var inputCount = 0;
				
		$('#addInput').on('click',function() {
								
			inputCount++;
								
			$('#inputs').append("<div class='ui right labeled input' "
					+"style='margin-top: 5px; margin-bottom: 5px;'>"
					+"<input name='inputValue'  type='text'>"
					+"<a class='ui tag label'>"
				    +inputCount
					+"번</a></div>");
		});

		$('#delInput').on('click', function() {
						
			if(inputCount >0){
				inputCount--;		
				$('#inputs div').last().remove();
			}
		});

		var fileTarget = $('.filebox .upload-hidden');

		fileTarget.on('change', function() { // 값이 변경되면

			if (window.FileReader) { // modern browser
				var filename = $(this)[0].files[0].name;	
			} else { // old IE	
				var filename = $(this).val().split('/').pop()
										.split('\\').pop(); // 파일명만 추출
			}
	
		// 추출한 파일명 삽입
		$(this).siblings('.upload-name').val(filename);
	
		});		
	});
</script>
<style type="text/css">
.filebox input[type="file"] {
	position: absolute;
	width: 1px;
	height: 1px;
	padding: 0;
	margin: -1px;
	overflow: hidden;
	clip: rect(0, 0, 0, 0);
	border: 0;
}

.filebox label {
	display: inline-block;
	padding: .5em .75em;
	color: #999;
	font-size: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #fdfdfd;
	cursor: pointer;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
}

/* named upload */
.filebox .upload-name {
	display: inline-block;
	padding: .5em .75em; /* label의 패딩값과 일치 */
	font-size: inherit;
	font-family: inherit;
	line-height: normal;
	vertical-align: middle;
	background-color: #f5f5f5;
	border: 1px solid #ebebeb;
	border-bottom-color: #e2e2e2;
	border-radius: .25em;
	-webkit-appearance: none; /* 네이티브 외형 감추기 */
	-moz-appearance: none;
	appearance: none;
}
</style>
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
		<h1>문제 업로드</h1>
	</div>

	<div class="container">
		<br />
		<form:form
			action="/score/add/problem?${_csrf.parameterName}=${_csrf.token}"
			method="post" enctype="multipart/form-data">
			<div class="row">

				<div class="col-md-8">

					<div class="bs-callout bs-callout-danger">
						<h4>과목 선택</h4>
						<select class="form-control" name="projectIdx">
							<option value="1">전산학실습</option>
							<option value="2">프로그래밍언어</option>
						</select>
					</div>
					<div class="bs-callout bs-callout-danger">
						<h4>문제 제목</h4>
						<input name="problemName" class="form-control" type="text">
					</div>


					<div class="bs-callout bs-callout-warning">
						<h4>문제 내용</h4>
						<textarea name="problemContents" class="form-control" rows="10"></textarea>
					</div>
					<div>
						<button class="ui huge right floated inverted red button" type="submit">문제 등록</button>
					</div>
				</div>
				<div class="col-md-4">

					<div class="bs-callout bs-callout-primary">
						<h4>소스 코드</h4>
						<div class="filebox">
							<input class="upload-name" value="파일선택" disabled="disabled">
							<label for="ex_filename">찾기</label> <input type="file"
								name="sourceFile" id="ex_filename" class="upload-hidden">
						</div>
					</div>
					<div class="bs-callout bs-callout-danger">
						<h4>입력 값</h4>
						<div class="ui big buttons">
							<button id="addInput" type="button"
								class="ui inverted green button">추가</button>
							<div class="or"></div>
							<button id="delInput" type="button" class="ui inverted red button">
								제거</button>
						</div>
						<aside style="height: 5px;"></aside>
						<div id="inputs"></div>
						
					</div>

				</div>
			</div>
		</form:form>
	</div>
</body>
</html>