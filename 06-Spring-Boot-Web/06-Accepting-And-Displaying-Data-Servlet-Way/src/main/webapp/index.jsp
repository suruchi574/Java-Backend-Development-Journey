<%@page language="java" %>
<html>
	<head>
		<link rel = "stylesheet" type = "text/css" href = "style.css">
	</head>
	<body>
		<h2>Suruchi's Calculator</h2>
		<form action = "add">
			<label for ="num1">Enter 1st Number</label>
			<input type = "text" id = "num1" name = "num1"><br>
			<label for ="num2">Enter 2nd Number</label>
			<input type = "text" id = "num2" name = "num2"><br>
			<input type = "submit" value = "Submit">
		</form>
	</body>
</html>
<!--http://localhost:8080/add?num1=23&num2=32 
	When we clicks Submit button it land in this webpage
	As we have not mapped this page we are getting the error 
	i.e There was an unexpected error (type=Not Found, status=404)-->


<!--Index.jsp is a view technology 
-- Request will go to view controller
-- We have not created controller that's why it's not working  
-->