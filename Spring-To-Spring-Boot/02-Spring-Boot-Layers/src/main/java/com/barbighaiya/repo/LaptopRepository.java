package com.barbighaiya.repo;

import org.springframework.stereotype.Repository;

import com.barbighaiya.model.Laptop;

@Repository
/*
 * @Repository is a Stereotype annotation
 * This we use in Repository layer
 * It allows Spring to manage it as a bean 
 * and also handle any
 * database-related exceptions.*/
public class LaptopRepository {
	
	public void save(Laptop lap) {
		System.out.println("Saved in Database✌");
	}

}
