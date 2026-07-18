package com.barbighaiya.repo;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.barbighaiya.Model.Student;

/*
 * Repository Layer: 
 * Manages interactions with the database for saving and retrieving data
 * i.e save(Student s), findAll()
 * These methods are interacting with database */

/*
 * In Normal JDBC
 * executeUpdate() - insert, update, delete
 * executeQuery() - Select*/

/*
 * In Spring JDBC
 * update() == executeUpdate() 
 * used for insert,update,delete*/

@Repository
public class StudentRepo {
	/*
	 * Spring provides the **JdbcTemplate** class.
	 * Instead of writing all the JDBC code manually, 
	 * we simply call its methods
	 * we use `JdbcTemplate` to insert student data into the database.*/
	private JdbcTemplate jdbc; 
	
	public void save(Student s) {
		//System.out.println("Saved");
		String sql = "insert into student (name,rollNo,marks) values (?,?,?)";
		int rows = jdbc.update(sql,s.getName(), s.getRollNo(), s.getMarks());
		System.out.println(rows + "effected");
	}

	public List<Student> findAll() {
		List<Student> student = new ArrayList<>();
		return student;
	}
	
	/*
	 * Getter and Setter for JDBC */
	public JdbcTemplate getJdbc() {
		return jdbc;
	}
	@Autowired
	public void setJdbc(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

}
