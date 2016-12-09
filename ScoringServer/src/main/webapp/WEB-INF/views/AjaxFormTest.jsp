<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<sec:csrfMetaTags />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function ajaxFormTest() {

		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		//폼객체를 불러와서
		var form = $('form').serialize();
		//FormData parameter에 담아줌

		$.ajax({
			type : "POST",
			data : form,
			dataType : "json",
			url : "./test/test",
			beforeSend : function(xhr) {
				// here it is
				xhr.setRequestHeader(csrfHeader, csrfToken);
			},
			success : function(obj) {
				$("#result").text(obj);
			},
			complete : function(data) {// 응답이 종료되면 실행, 잘 사용하지않는다

			},
			error : function(xhr, status, error) {
				alert("fail");
			}
		});
	};
</script>
</head>
<body>
	<form>
		<input type="text" name="num" placeholder="1"> <input
			type="text" name="name" placeholder="wj"> <input
			type="button" value="전송" onclick="ajaxFormTest()">
	</form>
	<div id="result"></div>
</body>
</html>