package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    	//Alien obj =  new Alien(); Manual way to create object
        Alien obj = (Alien)context.getBean("alien"); 
        /* 
         * getBean gives the object in type of Object 
         * That's why we have to typeCast it to Alien Object
         */
    	obj.code();
    }
}
