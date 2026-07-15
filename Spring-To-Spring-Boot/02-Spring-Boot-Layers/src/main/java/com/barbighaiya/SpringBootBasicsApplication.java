package com.barbighaiya;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.barbighaiya.model.Alien;
import com.barbighaiya.model.Laptop;
import com.barbighaiya.service.LaptopService;

@SpringBootApplication
public class SpringBootBasicsApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootBasicsApplication.class, args);
		//System.out.println("Welcome to my First Spring-Boot Project");
		
		
		LaptopService service = context.getBean(LaptopService.class);
		Laptop lap = context.getBean(Laptop.class);
		service.addLaptop(lap);
		
		
//		Alien alien = context.getBean(Alien.class);
//		alien.code();
//		System.out.println(alien.getAge());
	}

}

/* Op after all annotation
 * Coding
 * Compiling from Laptop
 * 23*/
