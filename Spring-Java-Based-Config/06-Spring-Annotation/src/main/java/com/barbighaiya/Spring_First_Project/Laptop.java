package com.barbighaiya.Spring_First_Project;

import org.springframework.stereotype.Component;

@Component
public class Laptop implements Computer {
	@Override
	public void compile()
	{
		System.out.println("Compiling from Laptop");
	}
	public Laptop()
	{
		System.out.println("Laptop Object Created");
	}
}
