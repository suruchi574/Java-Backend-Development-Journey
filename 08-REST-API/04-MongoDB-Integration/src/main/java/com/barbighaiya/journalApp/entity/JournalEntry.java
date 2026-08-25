package com.barbighaiya.journalApp.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="journal_entries") //marks class as a row and assigns the table name as journal_entries 
public class JournalEntry {
	/*
	 * We have created the JournalEntry here 
	 * By the help of POJO
	 * we have created here how our Journal entry app will look */
	@Id // marks id as a primary key
	private String id;
	private String title;
	private String content;
	private Date date;
	
	// Getter & Setters 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
