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
         * Looks for methods annotated with @Bean.
         * Calls the desktop() method.
         * Receives the Desktop object.
         * Stores it in the IoC Container.*/
        Desktop dt = context.getBean(Desktop.class); 
        /*
         * Searches the IoC Container for a bean of type Desktop.
         * Finds the object created by the desktop() method.
         * Returns that object.
        */
        dt.compile();
        
        /*
         * Output of Java Based Config: 
         * Desktop Object Created
         * Compiling from Desktop*/
       
    }
}

