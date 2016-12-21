<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table>
	<tr>
		<td></td>
		<c:forEach items="${problems}" var="problem" varStatus="status">
			<td>${problem.problemName}</td>
		</c:forEach>
	</tr>
	<tr>
		<td>ID</td>
		<c:forEach items="${problems}" var="problem" varStatus="status">
			<td>${status.count}</td>
		</c:forEach>
	</tr>

	<c:forEach items="${exports}" var="export" varStatus="status">
		<tr>
			<td>${export.id}</td>
			<c:forEach items="${problems}" var="problem" varStatus="pstatus">
				<td><c:out value="${export.infoMap[problem.problemIdx]}" default="0" /></td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>
