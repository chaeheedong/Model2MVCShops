<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page contentType="text/html; charset=EUC-KR" %>
<<<<<<< HEAD
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
=======
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>    
>>>>>>> branch 'master' of https://github.com/chaeheedong/Model2MVCShops.git

<html>
<head>

<title>��� ��ǰ ����</title>

</head>
<body>
<<<<<<< HEAD
	����� ��� ��ǰ�� �˰� �ִ�.
<br/>
<br/>
<%-- <%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Cookie[] cookies = request.getCookies();		// cookie�� �����´�.
	if (cookies != null && cookies.length > 0) {	// cookie�� null�� �ƴϰų� ���̰� 0���� Ŭ��
		for (int i = 0; i < cookies.length; i++) {	// ��Ű ���̸�ŭ ������.
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
<c:forEach var="list" items="${ list }">
<a href="/product/getProduct?prodNo=${ list }&menu=search"
	target="rightFrame">${ list }</a>
<br/>
</c:forEach>

<%-- <%
				}
			}
		}
	}
%> --%>
=======
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>

<c:forEach var="list" items="${ list }">
<a href="/product/getProduct?prodNo=${ list }&menu=search"
	target="rightFrame">${ list }</a>
</c:forEach>
<br>
>>>>>>> branch 'master' of https://github.com/chaeheedong/Model2MVCShops.git

</body>
</html>