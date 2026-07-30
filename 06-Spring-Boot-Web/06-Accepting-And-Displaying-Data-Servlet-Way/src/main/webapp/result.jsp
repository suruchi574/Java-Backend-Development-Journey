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
		<h2>Output is: ${result} </h2>
	</body>
</html>