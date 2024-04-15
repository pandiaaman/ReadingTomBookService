package com.readingTom.bookService.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.services.BookAuthorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bookauthor")
public class BookAuthorController {

	@Autowired
	private BookAuthorService bookAuthorService;

	public BookAuthorController(BookAuthorService bookAuthorService) {
		super();
		log.info("inside BookAuthorController...");
		this.bookAuthorService = bookAuthorService;
	}
	
	//get all authors
	@GetMapping(value = "/all", produces = {"application/json","application/xml"})
	public ResponseEntity<List<BookAuthor>> getAllBookAuthors(){
		try {
			log.info("BookAuthorController:: inside getAllBookAuthors:: getting all authors");
			
			List<BookAuthor> fetchedAuthors = this.bookAuthorService.getAllBookAuthors();
			
			return ResponseEntity.status(HttpStatus.OK).body(fetchedAuthors);
			
		}catch(Exception e) {
			log.error("Exception in getting all authors from the system...");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	//get author by id
	@GetMapping(value = "/{authorId}", produces= {"application/json","application/xml"})
	public ResponseEntity<BookAuthor> getOneAuthor(@PathVariable String authorId) {
		try {
			log.info("BookAuthorController:: getOneAuthor:: getting book with id " + authorId);
			BookAuthor author = this.bookAuthorService.getBookAuthorsById(authorId);
			
			return ResponseEntity.status(HttpStatus.OK).body(author);
		}catch(Exception e) {
			log.error("error in getting the author");
			e.printStackTrace(); //TODO we can throw the author not found exception here (custom exception)
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//add all authors
	@PostMapping(value = "/add",
			produces= {"application/json","application/xml"},
			consumes= {"application/json","application/xml"})
	public ResponseEntity<BookAuthor> addBookAuthor(@RequestBody BookAuthor bookAuthor) {
		try {
			log.info("BookAuthorController:: addBookAuthor:: adding the book...");
			BookAuthor addedBookAuthor = this.bookAuthorService.addBookAuthor(bookAuthor);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedBookAuthor);
		}catch(Exception e) {
			log.error("error in adding the book author");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		 
	}
	
}
