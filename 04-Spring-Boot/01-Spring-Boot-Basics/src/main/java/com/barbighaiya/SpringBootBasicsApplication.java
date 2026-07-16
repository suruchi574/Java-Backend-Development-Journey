package com.barbighaiya;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootBasicsApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootBasicsApplication.class, args);
		//System.out.println("Welcome to my First Spring-Boot Project");
		
		Alien alien = context.getBean(Alien.class);
		alien.code();
		System.out.println(alien.getAge());
	}

}

/* Op after all annotation
 * Coding
 * Compiling from Laptop
 * 23*/
