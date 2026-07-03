package com.barbighaiya.Spring_First_Project;

import java.beans.ConstructorProperties;

public class Alien {
	
	private int age;
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
	public void setComp(Computer comp) {
		this.comp = comp;
	}
	public Alien()
	{
		//System.out.println("Alien Object Created");
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		//System.out.println("Age variable Setter called");
		this.age = age;
	}
}
