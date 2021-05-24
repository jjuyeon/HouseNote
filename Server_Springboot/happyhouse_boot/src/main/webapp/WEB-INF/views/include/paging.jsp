<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- jstl의 core 라이브러리를 사용하기 위해 taglib를 이용한다. --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%--절대경로를 쉽게 사용하기 위해 session에 root를 key로 컨텍스트 루트 경로를 저장해둔다. --%>
<c:set value="${pageContext.request.contextPath }" scope="session" var="root"></c:set>
<center>
<table>
	<tr>
		<td>
			<ul class="pagination">
				<li class="page-item" data-pg="${navigation.startPage }"><a href="#" class="page-link">최신</a>
				</li>
				<li class="page-item" data-pg="${navigation.startRange ? navigation.startPage : navigation.currentPage - 1 }"><a href="#" class="page-link">이전</a>
				</li>
				<c:forEach var="i" begin="${navigation.startPage}" end="${navigation.endPage}">
					
					<li class="${navigation.currentPage == i ? 'page-item active' : 'page-item'}" data-pg="${i}"><a href="#" class="page-link">${i}</a></li>
				</c:forEach>
				<li class="page-item" data-pg="${navigation.endRange ? navigation.totalPageCount : navigation.currentPage + 1 }"><a href="#" class="page-link">다음</a>
				</li>
				<li class="page-item" data-pg="${navigation.totalPageCount }"><a href="#" class="page-link">마지막</a>
				</li>
			</ul>
		</td>
	</tr>
</table>
</center>