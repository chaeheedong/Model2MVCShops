<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${ purchase.purchaseProd.prodNo }</td>
	</tr>
	<tr>
		<td>�����ھ��̵�</td>
		<td>${ user.getUserId() }</td>
	</tr>
	<tr>
		<td>���Ź��</td>
		<c:choose>
			<c:when test="${ purchase.paymentOption eq '1'}">
				<td>
						���ݱ���
				</td>
				</c:when>
				<c:otherwise>
				<td>
						�ſ뱸��
				</td>
				</c:otherwise>
		</c:choose>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${ purchase.receiverName }</td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${ purchase.receiverPhone }</td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${ purchase.divyAddr }</td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${ purchase.divyRequest }</td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${ purchase.divyDate }</td>
	</tr>
</table>
</form>

</body>
</html>