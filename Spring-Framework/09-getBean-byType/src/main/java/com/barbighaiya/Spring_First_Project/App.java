package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        /*Alien obj = (Alien)context.getBean("alien1");
         * If we will call the bean by id of the bean 
         * Then it gives the object of Object class 
         * That's why we need to manually typecast it into respective class 
         * We can solve this problem by mentioning class name inside paranthesis*/
        
        Alien obj = context.getBean("alien1", Alien.class);
        
        Desktop desk = context.getBean(Desktop.class);
        /*
         * If we will not mention id name in getBean() method
         * It search the bean byType and creates it */
        
        Computer comp = context.getBean(Computer.class);
        /*if more than one bean is present of same type
         *  it creates bean which is having primary =true attribute
        */
        /*
         * getBean() byType Output: 
         * Desktop Object Created
			23
			Coding....
			Compiling from Laptop

         * */
        
        
        //obj.age = 23; Manual way to assign value
        //obj.setAge(21); Rather this we can use setter injection
        
        System.out.println(obj.getAge());
        obj.code();
        
        /*Alien obj2 = (Alien)context.getBean("alien1");  //creates another object
        System.out.println(obj2.age);*/
        
    	/*Desktop desk = (Desktop)context.getBean("comp2");
    	 * Manually calling lazy bean (desktop) to create bean*/
    	
    }
}

