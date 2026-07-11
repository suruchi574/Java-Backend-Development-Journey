package com.barbighaiya.Spring_First_Project;

import java.beans.ConstructorProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class Alien {
	
	@Value("23")
	private int age;
	//@Autowired - Field Autowiring also we can do
	private Computer comp; //It is a interface not a class 
	
	
	/*@ConstructorProperties({"age","lap"}) : required For name type 
	public Alien(int age, Laptop lap)
	{
		System.out.println("Parameterized constructor called");
		this.age=age;
		this.lap=lap;
	}*/
	public void code()
	{
		System.out.println("Coding....");
		comp.compile();
	}
	
	public Computer getComp() {
		return comp;
	}
	/*
	 * Here in Setter Method of Computer Interface
	 * We can pass object of any class which implements it 
	 * Ex: Laptop object or Desktop Object
	 * Based on Which object we are passing
	 *  it will call that class compile method*/
	
	@Autowired  // Constructor Injection
	@Qualifier("laptop")
	/*
	 * Higher priority as compare to primary
	 * As we have specified laptop here
	 * First Laptop object will be created not Desktop*/
	@Scope("protype") 
	public void setComp(Computer comp) {
		this.comp = comp;
	}
	public Alien()
	{
		System.out.println("Alien Object Created");
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		//System.out.println("Age variable Setter called");
		this.age = age;
	}
}
