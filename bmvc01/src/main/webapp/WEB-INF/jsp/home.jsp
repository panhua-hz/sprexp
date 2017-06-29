<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page import="java.util.List"
	import="form.ProdForm"
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
</head>
<body>
	<h1>
		<s:message code="app.welcome" />
	</h1>
	<div>
	<sf:form method="POST" modelAttribute="prodForm" acceptCharset="UTF-8">
		<!-- cloud setup cssClass/cssErrorClass here -->
		<sf:label path="prodName">Product Name</sf:label>
		<sf:input path="prodName"/>
		<sf:errors path="prodName"/>
		<br>
		<input type="submit" value="Add Prod to Cart" />
	</sf:form>
	
	<c:if test="${not empty recentProd}">
		<br>
		Recent Add Prod: <c:out value="${recentProd.prodName}"/>
	</c:if>
	</div>
	
	<div>
		<h3>Shopping Cart</h3>
		<dl>
			<c:forEach items="${prodList}" var="prod">
				<dt>
					hi prod:
				</dt>
				<dd>
					<c:out value="${prod}" />
				</dd>
			</c:forEach>
		</dl>
	</div>
	<div>
		<h3>request parameters</h3>
		<a href="<c:url value="/query?prodName=red apple" />">RPC Query</a>
		<br>
		<a href="<c:url value="/query/prodName/red apple" />">Resource Query</a>
		<br>
		<a href="<c:url value="/npexpt" />">NullPointException</a>
		<br>
		<a href="<c:url value="/oobexpt" />">IndexOutOfBoundsException</a>
		<br>
	</div>
	<div>
		<h3>文件上传</h3>
		<form action="file" method="post" enctype="multipart/form-data">
			<label>Profile Picture: </label>
			<input type="file" name="uploadFile" accept="image/jpeg,image/png,image/gif,image/jpg"/><br/>
			<input type="submit" value="UploadFile" />
		</form>
	</div>	
</body>
</html>
