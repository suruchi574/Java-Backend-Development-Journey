package com.barbighaiya.SpringBootWeb1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/*
 * @Controller
 * It is a Stereotype annotation.
 * It defines that this class handles web requests and controls the flow of the web application.
 *
 * Uses:
 * - Marks a class as a Spring MVC controller.
 * - Maps URLs to handler methods.
 * - Interacts with service classes to process business logic.
 *
 * Dependency required in pom.xml:
 *
 * <dependency>
 *     <groupId>org.apache.tomcat.embed</groupId>
 *     <artifactId>tomcat-embed-jasper</artifactId>
 * </dependency>
 *
 * Version is managed by Spring Boot.
 *
 * DispatcherServlet is responsible for receiving and mapping all the requests.
 */

@Controller
public class HomeController {

	// Maps "/" (home page) URL to the home() method.
	@RequestMapping("/")
	public String home() {
		System.out.println("Home method called");
		return "index.jsp";
	}

	/*
	 * We can map multiple requests in one controller.
	 *
	 * To accept the values we have two ways:
	 * 1. Servlet way
	 * 2. Spring way
	 */

	/*
	 * HttpSession
	 *
	 * Used to create and manage a session between client and server.
	 * Maintains user data across multiple HTTP requests.
	 * Common methods:
	 * - setAttribute()
	 * - getAttribute()
	 * - invalidate()
	 */

	/*
	 * Servlet way
	 *
	 * @RequestMapping("add")
	 * public String add(HttpServletRequest req, HttpSession session) {
	 *
	 *     int num1 = Integer.parseInt(req.getParameter("num1"));
	 *     int num2 = Integer.parseInt(req.getParameter("num2"));
	 *
	 *     int result = num1 + num2;
	 *
	 *     session.setAttribute("result", result);
	 *
	 *     return "result.jsp";
	 * }
	 */

	/*
	 * Spring way
	 *
	 * If parameter names are same as the request parameters,
	 * Spring maps them automatically.
	 *
	 * public String add(int num1, int num2, HttpSession session)
	 *
	 * If variable names are different,
	 * use @RequestParam("actualParameterName").
	 *
	 * Example:
	 *
	 * public String add(@RequestParam("num1") int num3,
	 *                   @RequestParam("num2") int num4,
	 *                   HttpSession session)
	 */

	/*
	 * Model Object
	 *
	 * Package:
	 * import org.springframework.ui.Model;
	 *
	 * Used to pass data from Controller to View (JSP/Thymeleaf).
	 * Can be used instead of HttpSession for passing request data.
	 *
	 * model.addAttribute("result", result);
	 */

	/*
	 * ViewResolver
	 *
	 * Instead of returning "result.jsp",
	 * we return only "result".
	 *
	 * This keeps the controller independent of the view technology.
	 *
	 * application.properties
	 *
	 * spring.mvc.view.prefix=/view/
	 * spring.mvc.view.suffix=.jsp
	 *
	 * prefix -> folder where JSP files are present
	 * suffix -> file extension
	 */

	@RequestMapping("add")
	public String add(@RequestParam("num1") int num3,
					  @RequestParam("num2") int num4,
					  Model model) {

		int result = num3 + num4;
		model.addAttribute("result", result);

		return "result";
	}
}