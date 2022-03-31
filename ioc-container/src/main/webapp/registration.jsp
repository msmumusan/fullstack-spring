<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Using IoC Container</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

</head>
<body>

	<div class="container mt-4">

		<h1>Using IoC Container</h1>



		<h3>Registration</h3>

		<div>
			 <c:url var="addRegister" value="/registration-edit">
				<c:param name="openClassId" value="${ openClass.id }"></c:param>
			</c:url> 

			<a class="btn btn-primary" href="${ addRegister }">Registration for the
				New Class</a>
		</div>


		<c:choose>
			<c:when test="${ empty registration }">
				<div class="alert alert-warning mt-4">There is no registration
					. Please Register.</div>
			</c:when>

			<c:otherwise>
				<table class="table table-striped">

					<thead>
						<tr>
							<th>ID</th>
							<th>Student Name</th>
							<th>Student Phone</th>
							<th>Student Email</th>		
						
						</tr>
					</thead>
					
					<tbody>

						<c:forEach var="c" items="${ registration }">

							<tr>
							
								<td>${ c.id }</td>
								<td>${ c.student }</td>
								<td>${ c.phone }</td>
								<td>${ c.email }</td>
							<tr>
								


						</c:forEach>
					</tbody>

					

				</table>

			</c:otherwise>

		</c:choose>

	</div>

</body>
</html>

