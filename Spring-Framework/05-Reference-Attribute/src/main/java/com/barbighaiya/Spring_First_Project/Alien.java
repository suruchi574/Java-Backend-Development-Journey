package com.barbighaiya.Spring_First_Project;

public class Alien {
	
	private int age;
	private Laptop lap;
	public Laptop getLap() {
		return lap;
	}
	public void setLap(Laptop lap) {
		this.lap = lap;
		System.out.println("Laptop object Setter called");
	}
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		System.out.println("Age variable Setter called");
		this.age = age;
	}
	public void code()
	{
		System.out.println("Coding....");
		lap.compile();
	}
	public Alien()
	{
		System.out.println("Alien Object Created");
	}
}
