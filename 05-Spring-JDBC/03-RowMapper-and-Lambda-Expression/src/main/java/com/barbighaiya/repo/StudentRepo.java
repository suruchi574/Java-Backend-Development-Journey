package com.barbighaiya.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

//	public List<Student> findAll() {
////		List<Student> student = new ArrayList<>();
////		return student;
//		
//		/*
//		 * RowMapper is a functional interface (We can use lamda expression)
//		 * Which helps us to fetch the data from ResultSet
//		 * ResultSet rs -> We get it after executing the query
//		 * int rowNum -> takes the row wise 
//		 * The job of mapRow is taking 1 row (object / data) at a time and give it to us */
//		String sql = "select * from Student";
//		
//		RowMapper<Student> mapper = new RowMapper<>() {
//			@Override
//			public Student mapRow(ResultSet rs, int rowNum) throws SQLException 
//			{
//				/*
//				 * We are creating a Student Object 
//				 * And fetching the value from ResultSet 
//				 * And assigning it to student object
//				 * Then we are  returning Student object */
//				Student st = new Student();
//				st.setName(rs.getString("name"));
//				st.setRollNo(rs.getInt("rollNo"));
//				st.setMarks(rs.getInt("marks"));
//				
//				return st;
//			}
//			
//		};
//		/*
//		 * jdbc.query(sql query , RowMapper ref)
//		 * This method is taking the Student data 
//		 * Which is stored in RowMapper
//		 * by performing select query
//		 * And returning the Student object */
//		List<Student> student = jdbc.query(sql, mapper);
//		return student;
//	}
//	
	public List<Student> findAll(){
		String sql = "select * from Student";
		
		/*
		 * Lamda Expression*/
//		RowMapper<Student> mapper = (ResultSet rs,int rowNum) -> {
//			Student st = new Student();
//			st.setName(rs.getString("name"));
//			st.setRollNo(rs.getInt("rollNo"));
//			st.setMarks(rs.getInt("marks"));
//			return st;
//			
//		};
//		return jdbc.query(sql, mapper);
		return jdbc.query(sql, (rs,rowNum) -> {
		Student st = new Student();
		st.setName(rs.getString("name"));
		st.setRollNo(rs.getInt("rollNo"));
		st.setMarks(rs.getInt("marks"));
		return st;
		
	});
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
