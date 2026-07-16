package com.example.demo;

import org.springframework.stereotype.Component;

/*
 * Component Annotation is used to get spring know that this class object needs to be created
*/
@Component
public class Laptop {
	public void compile()
	{
		System.out.println("Compiling");
	}
}
