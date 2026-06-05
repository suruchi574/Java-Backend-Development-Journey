package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*Component annotation we use for telling the Spring-Boot that this class object you have to manage*/
@Component
public class Alien {
	/*
	 * Autowired annotation is used to get do connect the one or more class.
	 * So that one class can use other class object which is created by spring boot
	*/
	@Autowired
	Laptop lap;
	public void code()
	{
		lap.compile();
	}
}
