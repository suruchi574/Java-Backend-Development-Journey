package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        Alien obj = (Alien)context.getBean("alien1"); // creates one object
        //obj.age = 23; Manual way to assign value
        //obj.setAge(21); Rather this we can use setter injection
        
        System.out.println(obj.getAge());
        
        /*Alien obj2 = (Alien)context.getBean("alien1");  //creates another object
        System.out.println(obj2.age);*/
    	
    	
    }
}

/* Output of Constructor injection
 * Laptop Object Created
 * Parameterized constructor called
 * 23
 */
