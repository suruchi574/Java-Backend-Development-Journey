package com.barbighaiya.journalApp.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.barbighaiya.journalApp.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, String>{

}
