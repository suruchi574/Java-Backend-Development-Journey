package com.barbighaiya.Spring_First_Project;

public class Alien {
	
	int age;
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		System.out.println("Setter called");
		this.age = age;
	}
	public void code()
	{
		System.out.println("Coding....");
	}
	public Alien()
	{
		System.out.println("Alien Object Created");
	}
}
