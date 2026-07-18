package com.barbighaiya.repo;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.barbighaiya.Model.Student;

/*
 * Repository Layer: 
 * Manages interactions with the database for saving and retrieving data
 * i.e save(Student s), findAll()
 * These methods are interacting with database */

@Repository
public class StudentRepo {

	public void save(Student s) {
		System.out.println("Saved");
		
	}

	public List<Student> findAll() {
		List<Student> student = new ArrayList<>();
		return student;
	}

}
