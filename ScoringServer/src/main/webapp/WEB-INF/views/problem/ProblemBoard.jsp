<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="../include/tags.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sec:csrfMetaTags />
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
<link rel="stylesheet"
	href="/score/resources/css/table.css">
<link rel="stylesheet"
	href="/score/resources/css/button.css">

<style type="text/css">
.popover {
  width: 600px;
}
</style>

<script type="text/javascript">

	$(document).ready(function() {

	    $("button[name=confirm]").popover({
	        html : true, 
	        content: function() {
	          var div=$(this).next();
	          return div.html();
	        },
	        title: function() {
	          return "결과";
	        }
	    });

		
		$('tr button[name="scoring"]').on('click', function() {
			var formData = new FormData();
			var trTag = $(this).closest('tr');
			var problemIdx;
			trTag.find('input').each(function(index){
				var inputName = $(this).attr('name');
				var inputValue;
			
				if(inputName != "sourceFile"){
					inputValue = $(this).val();
					if(inputName == "problemIdx"){
						problemIdx= $(this).val();
					}
				}else{
					inputValue = $(this)[0].files[0];
				}
				formData.append(inputName,inputValue);
			})
			
			var csrfHeader = $("meta[name='_csrf_header']").attr("content");
			var csrfToken = $("meta[name='_csrf']").attr("content");

			var obj=trTag.find('td[title=result]');
			obj.html("<button name='confirm' class='ui loading button' style='height:34px; width:54px;'>확인</button>");
			
			$.ajax({
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				url : "../ajax/scoring/add",
				beforeSend : function(xhr) {
					xhr.setRequestHeader(csrfHeader, csrfToken);
				},
				complete : function(data) {
					
					$.ajax({
						type : "GET",
						data : {"problemIdx":problemIdx},
						url : "../ajax/scoring/read",
						beforeSend : function(xhr) {
							xhr.setRequestHeader(csrfHeader, csrfToken);
						},
						success : function(obj) {
							var resultBody = trTag.find('td[title=result]');
							var scoreBody = trTag.find('td[title="score"]');
							scoreBody.empty();
							resultBody.empty();
							var inputHtml="<button name='confirm' class='btn btn-info'>확인</button>"
							+"<div style='display: none;'>"
							+"<table class='ui celled padded table'>"
							+"<tr><th>번호</th><th>입력</th><th>점수</th></tr>";
							$.each(obj.infos, function(index, info){
								if(index ==0){
									scoreBody.text("<h4>"+info.score+"</h4>");
									
								}else{
									inputHtml=inputHtml+"<tr>"
									+"<td>"+info.scoreNo+"</td>"
									+"<td>"+info.input+"</td>"
									+"<td>"+info.score+"</td>"
									+"</tr>"
								}
							});
							inputHtml= inputHtml+"</table></div>";
							resultBody.html(inputHtml);
							
							$("button[name=confirm]").popover({
						        html : true, 
						        content: function() {
						          var div=$(this).next();
						          return div.html();
						        },
						        title: function() {
						          return "결과";
						        }
						    });
						}
				
					});
				}
			
			});
			
		});
		

		
		
		var fileTarget = $('.filebox .upload-hidden');

		  fileTarget.on('change', function(){  // 값이 변경되면
		    if(window.FileReader){  // modern browser
		      var filename = $(this)[0].files[0].name;
		    } 
		    else {  // old IE
		      var filename = $(this).val().split('/').pop().split('\\').pop();  // 파일명만 추출
		    }
		    
		    // 추출한 파일명 삽입
		    $(this).siblings('.upload-name').val(filename);
		  });
	});
</script>
<!-- sockjs 사용해서 나중에 progress bar 넣기 -->
<style type="text/css">
table td,th{
	text-align: center;
	vertical-align: middle;
	
}
.filebox input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip:rect(0,0,0,0);
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
  padding: .5em .75em;  /* label의 패딩값과 일치 */
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
</head>
<body>

	<%@include file="../include/header.jsp"%>

	<div class="container">
		<h1>문제 풀기</h1>
	</div>

	<div class="container">
		<div class="row">
			<div class="col-md-9">
				<table class="table table-bordered table-striped bs-events-table">
					<thead>
						<tr>
							<th>문제</th>
							<th>파일</th>
							<th>채점</th>
							<th>점수</th>
							<th>결과</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${problemList}" var="problem" varStatus="pstatus">
							<tr id="tr_input${pstatus.count}">
								<td><input type="hidden" name="problemIdx"
									value="${problem.problemIdx}"><h4><a data-toggle="collapse"
									href="#collapse${pstatus.count}">${problem.problemName}</a></h4></td>
								<td>
								<div class="filebox">
								<input class="upload-name" value="파일선택" disabled="disabled">
								<label for="ex_filename${pstatus.count}">업로드</label> 
								<input type="file" name="sourceFile" id="ex_filename${pstatus.count}" class="upload-hidden">
								</div>
								</td>
								<td><button name="scoring" class="btn btn-info">채점</button></td>
								<td title="score">
								<c:forEach items="${scoreResults}" var="scoreResult">
									<c:if test="${scoreResult.problemIdx == problem.problemIdx}">
										<c:forEach items="${scoreResult.infos}" var="info" end="0">
											<h4>${info.score}</h4>
										</c:forEach>
									</c:if>
									</c:forEach>
								</td>
								<td title="result">	 
								<c:forEach items="${scoreResults}" var="scoreResult">
									<c:if test="${scoreResult.problemIdx == problem.problemIdx}">
									<button name='confirm' class="btn btn-info">확인</button>
									<div style="display: none;">
									<table class="ui celled padded table">
									<tr>
										<th>번호</th>
										<th>입력</th>
										<th>점수</th>
									</tr>
									</c:if>
								</c:forEach>
								
								
								<c:forEach items="${scoreResults}" var="scoreResult">
									<c:if test="${scoreResult.problemIdx == problem.problemIdx}">
										<c:forEach items="${scoreResult.infos}" var="info" begin="1">
										<tr>
										<td>${info.scoreNo}</td>
										<td>${info.input}</td>
										<td>${info.score}</td>
										</tr>
										</c:forEach>
										
										</table>
										</div>
									</c:if>
								</c:forEach>
								
								
								</td>
			
							</tr>
							<tr id="collapse${pstatus.count}" class="panel-collapse collapse">
								<td colspan="5"><h4>${problem.problemContents}</h4></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>	
</body>


</html>