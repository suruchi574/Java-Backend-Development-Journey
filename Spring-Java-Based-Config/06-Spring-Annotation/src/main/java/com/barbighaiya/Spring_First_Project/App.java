package com.barbighaiya.Spring_First_Project;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import config.AppConfig;

public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        /*
         * For Doing java Based configuration
         * We need to use AnnotationConfigApplicationContext constructor
         * AppConfig.class passes the java config to the constructor
         * Spring performs these steps:
         * Reads AppConfig.class.
         * Finds the @Configuration annotation.
         * Then spring finds @ComponentScan(package name of all class)
         * Spring refers class for all bean creations and management*/
        
        Alien obj = context.getBean(Alien.class);
        System.out.println(obj.getAge());
        obj.code();
        
        /*Desktop dt = context.getBean("barbighaiya",Desktop.class);
         * Here desktop is deafult bean name
         * And barbighaiya is specified bean name 
         * */ 
        
        /*
         * getBean("desktop",Desktop.class);
         * Searches the IoC Container for a bean of type Desktop.
         * Finds the object created by the desktop() method.
         * Returns that object.
        */
        
        
       
    }
}

