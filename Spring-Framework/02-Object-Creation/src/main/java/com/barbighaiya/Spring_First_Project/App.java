package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        /* ApplicationContext: 
         * When the ApplicationContext is instantiated,
         *  it automatically reads the XML configuration file,
         *  creates the container, 
         *  and instantiates all the beans defined in it. 
        */
        
    	//Alien obj =  new Alien(); Manual way to create object
        
        Alien obj = (Alien)context.getBean("alien1"); 
        obj.code();
        /* 
         * By using getBean we are getting the object from container
         * And by using obj.code() we are calling the object 
         * getBean gives the object in type of Object 
         * That's why we have to typeCast it to Alien Object
         */
        Alien obj2 = (Alien)context.getBean("alien1"); 
        obj2.code();
        /* OP:
         * Alien Object Created
         * Laptop Object Created
         * If we have initialized the bean tag one time for a particular class
        */

    	
    	
    }
}
