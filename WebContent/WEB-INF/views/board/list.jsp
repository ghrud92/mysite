<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/include/header.jsp"></c:import>
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board" method="get">
					<input type="text" id="kwd" name="kwd" value="${param.kwd }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:set var="count" value="${pageMap.total - 5*(pageMap.page - 1) }"></c:set>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${count - status.index }</td>
							<td style="text-align:left; padding-left:${20*vo.depth}px">
								<c:if test="${vo.depth > 0 }">
									<img src="${pageContext.request.contextPath}/assets/images/reply.jpg" width="15px">
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=view&no=${vo.no}&page=${pageMap.page}&kwd=${param.kwd}">${vo.title }</a>
							</td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:choose>
								<c:when test="${vo.userNo == sessionScope.authUser.no }">
									<td>
										<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a>
									</td>
								</c:when>
								<c:otherwise>
									<td></td>
								</c:otherwise>
							</c:choose>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test="${pageMap.left == 1 }">
							<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.startPage - 5}&kwd=${param.kwd}">◀</a></li>
						</c:if>
						<c:forEach begin="${pageMap.startPage }" end="${pageMap.lastPage }" var="i">
							<c:choose>
								<c:when test="${i == pageMap.page }">
									<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${i}&kwd=${param.kwd}">${i }</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath }/board?page=${i}&kwd=${param.kwd}">${i }</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${pageMap.right == 1 }">
							<li><a href="${pageContext.request.contextPath }/board?page=${pageMap.lastPage + 1}&kwd=${param.kwd}">▶</a></li>
						</c:if>
					</ul>
				</div>
				<div class="bottom">
					<c:if test="${not empty sessionScope.authUser }">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</c:if>
				</div>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/include/navigation.jsp">
			<c:param name="location" value="board"></c:param>
		</c:import>
		<c:import url="/WEB-INF/views/include/footer.jsp"></c:import>
	</div>
</body>
</html>