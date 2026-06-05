package com.example.demo;

import org.springframework.stereotype.Component;

/*Component annotation we use for telling the Spring-Boot that this class object you have to manage*/
@Component
public class Alien {
	public void code()
	{
		System.out.println("Coding");
	}
}
