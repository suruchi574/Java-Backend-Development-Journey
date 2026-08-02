<%@page language="java" %>
<html>
	<head>
		<link rel = "stylesheet" type = "text/css" href = "style.css">
	</head>
	<body>
		<!--<h2>Output is: <%= session.getAttribute("result") %> </h2>-->
		<!--JSTL : Java Server Pages Standard tag library
		Eliminates the need for scriptlets (<% %>) in JSP pages
		Improves readability and maintainability of JSP files
		Provides built-in tags for iteration, conditional checks, and data formatting-->
		<h2>Object</h2>
		<!--<p>${alien1} </p>-->
		<p>${alien} </p>
		<h2>Welcome to ${course} world!</h2>
	</body>
</html>