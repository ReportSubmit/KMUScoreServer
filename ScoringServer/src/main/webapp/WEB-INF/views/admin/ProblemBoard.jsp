<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- 부가적인 테마 -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">

<!-- 합쳐지고 최소화된 최신 자바스크립트 -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		/*
		$('div.panel-info h3').on('click',function(){
			
		});
		 */
	});
</script>
<!-- sockjs 사용해서 나중에 progress bar 넣기 -->
</head>
<body>

	<%@include file="../include/header.jsp"%>

	<div class="container">
		<h1>홈</h1>
		<p>국민대 전자공학부 프로그래밍 채점 사이트입니다.</p>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<div class="panel-group" id="accordion" role="tablist"
					aria-multiselectable="true">
					<c:forEach items="${problemList}" var="problem" varStatus="status">
						<div class="panel panel-default">
							<div class="panel-heading" role="tab" id="heading${status.count}">
								<h4 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordion"
										href="#collapse${status.count}" aria-expanded="true"
										aria-controls="collapse${status.count}">
										${problem.problemName} </a> <span style="float: right">&nbsp;<input
										type="button" value="채점">
									</span><span style="float: right"><input type="file"
										name="sourceFile"></span>
								</h4>
							</div>
							<div id="collapse${status.count}"
								class="panel-collapse collapse <c:if test="${status.first}">in</c:if> "
								role="tabpanel" aria-labelledby="heading${status.count}">
								<div class="panel-body">${problem.problemContents}
									<span style="visibility: hidden;">${problem.problemIdx}"</span>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>
</body>


</html>