package com.barbighaiya.journalApp.entity;

public class JournalEntry {
	/*
	 * We have created the JournalEntry here 
	 * By the help of POJO
	 * we have created here how our Journal entry app will look */
	
	private long id;
	private String title;
	private String content;
	
	// Getter & Setters 
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	
	
}
