package com.barbighaiya;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Creating one small API

@RestController
public class MyClass {
	
	@GetMapping("hello")
	public String sayHello()
	{
		return "Hello from Spring Boot!";
	}
}


/*
 * To see output :
 * Go to browser and type 
 * localhost:8080/hello
 * We will get the o/p 
 * Hello from Spring Boot!
 */