package com.barbighaiya.SpringBootWeb1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
/*
 * Controller:
 * It is a Stereotype annotation 
 * It defines that this class handles web request and control the flow of web application
 * Uses: 
 * Marks a class as a Spring MVC controller for handling HTTP requests.
 * Maps URLs to specific handler methods using request mapping annotations.
 * Interacts with service classes to process business logic.*/

/*		<dependency>
    		<groupId>org.apache.tomcat.embed</groupId>
    		<artifactId>tomcat-embed-jasper</artifactId>
		</dependency>
		This dependency is required to add in pm.xml file to make the class as a controller
		As it don't accepts versions 
 * 
 * Dispatch Servlet is responsible to call and map all the request*/

@Controller
public class HomeController {
	/* @RequestMapping("/"): 
	 * Maps the / (home page) URL to the home() method.
	 * */
	@RequestMapping("/")
	public String home()
	{
		System.out.println("Home method called");
		return "index.jsp";
	}
	/*
	 * We can map multiple request in one controller
	 * To accepts the request we will create another method
	 * Which will return the web page and add() is the page name
	 * We need to accept the values so that we can perform further operation
	 * There is two way to do that : 
	 * 1. Servlet way
	 * 2. Spring way */
	
	/* HttpSession session 
	 * The HttpSession interface in Java Servlet is used to 
	 * create and manage a session between a client and a server.
	 * Maintains user data across multiple HTTP requests.
	 * Provides methods like setAttribute(), getAttribute(), and invalidate().
	 * Automatically creates a unique session ID for each user.
	 * */
	@RequestMapping("add")
	public String add(HttpServletRequest req,HttpSession session)
	{
		/*
		 * req.getParameter("num1"); return string we need to convert in integer */
		int num1 = Integer.parseInt(req.getParameter("num1"));
		int num2 = Integer.parseInt(req.getParameter("num2"));
		int result = num1+num2;
		//System.out.println("in add");
		
		/*setAttribute(String name , T data);*/
		session.setAttribute("result", result);
		return "result.jsp";
	}
	
}

