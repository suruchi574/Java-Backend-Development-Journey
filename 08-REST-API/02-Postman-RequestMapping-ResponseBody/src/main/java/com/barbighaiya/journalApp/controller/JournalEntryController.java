package com.barbighaiya.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbighaiya.journalApp.entity.JournalEntry;

@RestController
/*
 * By the help of @RequestMapping we are mapping a URL/ request
 * to a controller class or method*/
@RequestMapping("/journal")
public class JournalEntryController {
	/*
	 * This map will be having key in Long type like 1L, 2L, 3L
	 * And Values will be having the JournalEntry object which contains id, title and content
	 * like 1L -> 1L , Development , Learned to create API
	 * 
	*/
	private Map<Long, JournalEntry> journalEntries = new HashMap<>();
	/*
	 * If we are using @RequestMapping there is no need of adding the endpoint URL in get or post mapping
	 * But only we have to make sure that we can't create multiple method and @GetMapping
	 * */
	@GetMapping
	public List<JournalEntry> getAll(){
		/*Map returns the list of values in COllection format
		 * converting the Collection into ArrayList
		 * Because we are returning the values in List format
		 * */
		return new ArrayList<>(journalEntries.values());
		}
	@PostMapping
	/*
	 * @RequestBody:
	 * It is like saying hey spring please take the data from the request 
	 * and turn it into a java object that i can use in my code.*/
	public boolean createEntry(@RequestBody JournalEntry myEntry )
	{
		/*
		 * Adding the key and values in journalEntry map*/
		journalEntries.put(myEntry.getId(), myEntry);
		return true;
	}
	

}
