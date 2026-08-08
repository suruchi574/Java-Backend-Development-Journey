package com.barbighaiya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Component
@RestController
public class MyCar {
	
	@Autowired
	private MyBike bike;
	
	@GetMapping("/enjoy")
	public String enjoy()
	{
		return bike.fun();
	}
}
