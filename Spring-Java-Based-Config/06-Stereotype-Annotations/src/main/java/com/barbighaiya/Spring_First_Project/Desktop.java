package com.barbighaiya.Spring_First_Project;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Desktop implements Computer {
	
	@Override
	public void compile() {
		System.out.println("Compiling from Desktop");
	}
	public Desktop()
	{
		System.out.println("Desktop Object Created");
	}
}
