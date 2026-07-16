package com.barbighaiya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbighaiya.model.Laptop;
import com.barbighaiya.repo.LaptopRepository;


@Service
/*
 * Service is a Stereotype annotation
 * It works same as Component Stereotype annotation
 * When we are working with service class
 * Adding @Service annotation is a good practice
 * The @Service annotation is used to denote a class as a service provider, 
 * that allows Spring to detect and manage it as a bean in the application context.
 * ● The Service Class is responsible for:
 * ○ Containing the business logic of the application.
 * ○ Processing and handling data.
 * ○ Acting as an intermediary between the Controller and Repository
 * layers.
*/
public class LaptopService {
	
	@Autowired
	private LaptopRepository laprepo;
	
	public void addLaptop(Laptop lap) {
		//System.out.println("Service method called");
		laprepo.save(lap);
	}
	
	public boolean isGoodForProg(Laptop lap)
	{
		return true;
	}

}
