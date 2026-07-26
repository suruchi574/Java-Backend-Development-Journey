package com.barbighaiya.SpringBootWeb1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
 * */

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
}

