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
		Hello
	</h1>
	<div>
	<sf:form method="POST" modelAttribute="prodForm" acceptCharset="UTF-8">
		<sf:label path="prodName">Product Name</sf:label>
		<sf:input path="prodName"/>
		<br>
		<input type="submit" value="Add Prod to Cart" />
	</sf:form>
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
</body>
</html>
