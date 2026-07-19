package com.barbighaiya.Model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
 * Model Layer: 
 * Represents the entity or variables that maps to a table in the database.
 * In a Spring JDBC project, 
 * A class (ie. Student) represents a table in the database, 
 * And each object of the class represents a row in that table 
 * Student 
 * | name | rollNo | marks | 
 * */


@Component
@Scope("prototype")
public class Student {
	private String name;
	private int rollNo;
	private int marks;
	
	/*
	 * We have generated getter and setter methods of all variables
	 * to set and get the value
	 * */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRollNo() {
		return rollNo;
	}
	public void setRollNo(int rollNo) {
		this.rollNo = rollNo;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	/*
	 * We have used toString() method
	 * to print value of variable rather than address*/
	@Override
	public String toString() {
		return "Student [name=" + name + ", rollNo=" + rollNo + ", marks=" + marks + "]";
	}	
}
