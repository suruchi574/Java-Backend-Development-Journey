package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Alien obj = (Alien)context.getBean("alien1"); // creates one object
        obj.age = 23;
        System.out.println(obj.age);
        
        Alien obj2 = (Alien)context.getBean("alien1");  //creates another object
        System.out.println(obj2.age);
    	
    	
    }
}


/*0P:
	Laptop Object Created
	Alien Object Created
	23
	Alien Object Created
	0
*/