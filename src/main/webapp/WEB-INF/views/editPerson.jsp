<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Form</title>
<style>
	.error {
		color: red;
	}
</style>
</head>
<body>
	<form:form modelAttribute="person" action="/editPerson" method="post">
		<form:label path="firstName">firstName :</form:label>
		<br />
		<form:input path="firstName" value="${person.firstName}"/>
		<br />
		<form:errors path="firstName" cssClass="error" />
		<br />
		<form:label path="lastName">lastName :</form:label>
		<br />
		<form:input path="lastName" value="${person.lastName}"/>
		<br />
		<form:errors path="lastName" cssClass="error" />
		<br />
		<form:label path="age">age :</form:label>
		<br />
		<form:input type="number" path="age" value="${person.age}"/>
		<br />
		<form:errors path="age" cssClass="error" />
		<br />
		<input type="submit" value="Enregistrer">
	</form:form>
</body>
</html>