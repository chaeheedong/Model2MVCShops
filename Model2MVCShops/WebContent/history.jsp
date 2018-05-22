<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다.
	<c:forEach var="list" items="${ list }">
		<a href="/product/getProduct?prodNo=${ list }&menu=search"
			target="rightFrame">${ list }</a>
		<br/>
	</c:forEach>
<%-- <%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Cookie[] cookies = request.getCookies();		// cookie를 가져온다.
	if (cookies != null && cookies.length > 0) {	// cookie가 null이 아니거나 길이가 0보다 클때
		for (int i = 0; i < cookies.length; i++) {	// 쿠키 길이만큼 돌린다.
			Cookie cookie = cookies[i];				// 
			if (cookie.getName().equals("history")) {
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(",");
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) {
%> --%>

<%-- <%
				}
			}
		}
	}
%> --%>

</body>
</html>