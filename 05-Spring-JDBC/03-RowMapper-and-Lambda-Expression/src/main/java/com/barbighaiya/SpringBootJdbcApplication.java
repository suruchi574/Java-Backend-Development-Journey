package com.barbighaiya;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.barbighaiya.Model.Student;
import com.barbighaiya.service.StudentService;


/*
 * Application Layer: 
 * Acts as the entry point and coordinates the process of
 * Adding and Retrieving students.*/
@SpringBootApplication
public class SpringBootJdbcApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootJdbcApplication.class, args);
		//System.out.println("Welcome to my Spring-JDBC learning journey");
		Student s = context.getBean(Student.class);
		s.setName("Suruchi");
		s.setRollNo(61);
		s.setMarks(78);
		
		//System.out.println(s);
		StudentService service = context.getBean(StudentService.class);
		service.addStudent(s);
		
		List<Student> students = service.getStudents();
		System.out.println(students);
	}

}
