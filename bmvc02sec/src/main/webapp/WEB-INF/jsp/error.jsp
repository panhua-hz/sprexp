<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body>
	<div>
		<h3>Error Code</h3>

		<dl>
			<dt>
				<c:out value="${ExceptionType}" />
			</dt>

			<c:if test="${not empty customerID}">
				<dd>
					<c:out value="${customerID}" />
				</dd>
			</c:if>
			<dd>
				<c:out value="${message}" />
			</dd>
		</dl>

	</div>
</body>
</html>