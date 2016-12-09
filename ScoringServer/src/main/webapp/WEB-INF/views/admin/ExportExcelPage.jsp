<%@ page language="java"
	contentType="application/vnd.ms-excel; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table>

	<c:forEach begin="0" items="${scoreMap}" step="1" var="entry"
		varStatus="status">
		<tr>
			<td>${entry.key}</td>
			<c:set var="cnt" value="1" />
			<c:forEach begin="0" items="${entry.value}" step="1" var="item"
				varStatus="status">
				<c:set var="forCnt" value="${cnt}" />
				<c:forEach var="i" begin="${forCnt}" end="${item.probNum-1}">
					<c:set var="cnt" value="${cnt+1}" />
					<td>0</td>
				</c:forEach>
				<c:if test="${cnt == item.probNum}">
					<td>${item.score}</td>
					<c:set var="cnt" value="${cnt+1}" />
				</c:if>
			</c:forEach>
		</tr>

	</c:forEach>
</table>