package com.barbighaiya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.barbighaiya.Model.Student;
import com.barbighaiya.repo.StudentRepo;

/*
 * Service Layer: 
 * Contains the business logic
 * that interacts with the repository.
 * i.e repo.save(s); return repo.findAll();
 * These are repository layer methods */

@Service
public class StudentService {

	private StudentRepo repo;
	/*
	 * Created getter and setter methods for repository layer
	*/
	public StudentRepo getRepo() {
		return repo;
	}
	
	@Autowired
	public void setRepo(StudentRepo repo) {
		this.repo = repo;
	}
	
	/*
	 * These service layer methods are interacting with repository layer
	 * And passing the data to add and takes the data to return
	*/
	public void addStudent(Student s) {
		//System.out.println("Added");
		repo.save(s);
		
	}
	public List<Student> getStudents() {
		return repo.findAll();
		
	}

}
