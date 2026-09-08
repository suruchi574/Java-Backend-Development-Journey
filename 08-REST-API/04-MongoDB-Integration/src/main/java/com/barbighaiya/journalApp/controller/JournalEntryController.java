package com.barbighaiya.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbighaiya.journalApp.entity.JournalEntry;
import com.barbighaiya.journalApp.service.JournalEntryService;

@RestController
/*
 * By the help of @RequestMapping we are mapping a URL/ request
 * to a controller class or method*/
@RequestMapping("/journal")
public class JournalEntryController {
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@GetMapping
	public List<JournalEntry> getAll()
	{
		return null;
	}
	@PostMapping
	/*
	 * @RequestBody:
	 * It is like saying hey spring please take the data from the request 
	 * and turn it into a java object that i can use in my code.*/
	public boolean createEntry(@RequestBody JournalEntry myEntry )
	{
		journalEntryService.saveEntry(myEntry);
		return true;
	}
	
	@GetMapping("id/{myId}")
	/*
	 * @GetMapping("id/{myId}"):
	 * By writing the myId as variable in the GetMapping we are simply taking the id value from the url 
	 * So that we can fetch only that particular id value
	 * @PathVariable
	 * Used to extract values from the URL path.
	 * Value appears inside the URL path.
	 * Mainly used to identify a specific resource like an ID
	 * EX : /id/2
	 * @GetMapping("/id/{myId}*/
	public JournalEntry journalEntryByID(@PathVariable String myId)
	{
		return null;
	}
	/*
	 * @DeleteMapping:
	 * By writing the myId as variable in the DeleteMapping we are simply taking the id value from the URL 
	 * So that we can delete only that particular id value from journal Entry*/
	@DeleteMapping("id/{myId}")
	public JournalEntry deleteJournalEntryByID(@PathVariable String myId)
	{
		return null;
	}
	
	/*
	 * @PutMapping:
	 * By writing the id as variable in the PutMapping we are simply taking the id value from the URL 
	 * So that we can update only that particular id value from journal Entry*/
	@PutMapping("/id/{id}")
	public JournalEntry updateJournalById(@PathVariable String id, @RequestBody JournalEntry myEntry){
		return null;
	}
	 
}
