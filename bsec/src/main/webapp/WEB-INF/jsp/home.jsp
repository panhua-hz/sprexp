<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>
		<s:message code="app.welcome" /> <sec:authentication property="principal.username"/>
		<sf:form method="POST" action="logout">
			<input type="submit" value="Sign Out"/>
		</sf:form>
	</h1>
	
	<sec:authorize access="hasRole('ADMIN')">
		<div>
			You have ADMIN role.
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('EMPLOYEE')">
		<div>
			You have EMPLOYEE role.
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('MANAGER')">
		<div>
			You have MANAGER role.
		</div>
	</sec:authorize>
	<sec:authorize access="hasRole('GUEST')">
		<div>
			You have GUEST role.
		</div>
	</sec:authorize>		
</body>
</html>
