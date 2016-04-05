<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="navigation">
	<ul>
		<c:choose>
			<c:when test="${param.location == 'main' }">
				<li class="selected"><a href="${pageContext.request.contextPath }/main">김호경</a></li>
				<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
			</c:when>
			<c:when test="${param.location == 'guestbook' }">
				<li><a href="${pageContext.request.contextPath }/main">김호경</a></li>
				<li class="selected"><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
			</c:when>
			<c:when test="${param.location == 'board' }">
				<li><a href="${pageContext.request.contextPath }/main">김호경</a></li>
				<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
				<li class="selected"><a href="${pageContext.request.contextPath }/board">게시판</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath }/main">김호경</a></li>
				<li><a href="${pageContext.request.contextPath }/guestbook">방명록</a></li>
				<li><a href="${pageContext.request.contextPath }/board">게시판</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>