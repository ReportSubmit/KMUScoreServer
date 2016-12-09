<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){

	$('#addInput').on('click',function(){
		$('#inputs').append("<br/><input name='inputValue' type='text'>");
	});
	$('#delInput').on('click',function(){
		$('#inputs input').last().remove();
		$('#inputs br').last().remove();
	});

});

</script>
</head>

<body>
	<form:form
		action="/score/add/problem?${_csrf.parameterName}=${_csrf.token}"
		method="post" enctype="multipart/form-data">

		<select name="projectIdx">
			<option value="1">전산학실습</option>
			<option value="2">프로그래밍언어</option>
		</select>
		문제이름<input name="problemName" type="text">
		소스코드<input name="sourceFile" type="file">
		<br />
		문제내용<input name="problemContents" type="text" style="width: 50%; height: 500px;">
		
		<br />
		<input id="addInput" type="button" value="입력 개수 추가">
		<br />
		<input id="delInput" type="button" value="입력 개수 제거">
		<br />
		<div id="inputs"></div>
		<br />
		<input type="submit" value="확인">

	</form:form>
</body>
</html>