package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootIntroApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootIntroApplication.class, args);
		
		//System.out.println("Hello World");
		
		/*Alien obj = new Alien();
		obj.code();
		 Manual way to create object , We can do it through spring boot*/ 
		
		/*getBean(className.class) method is used to get particular class object which is present in container
		 By default spring-boot creates one object at a time*/
		Alien obj1  = context.getBean(Alien.class);
		obj1.code(); //compiling
		
	}

}
