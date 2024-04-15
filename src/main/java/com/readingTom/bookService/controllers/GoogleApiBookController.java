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

import com.readingTom.bookService.customException.GoogleApiBookNotFoundException;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.services.GoogleApiBookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/googleapibook")
public class GoogleApiBookController {
	
	@Autowired
	private GoogleApiBookService googleApiBookService;

	public GoogleApiBookController(GoogleApiBookService googleApiBookService) {
		super();
		log.info("inside GoogleApiBookCOntroller...");
		this.googleApiBookService = googleApiBookService;
	}
	
	//getting all books
	@GetMapping(value = "/all", produces= {"application/json","application/xml"})
	public ResponseEntity<List<GoogleApiBook>> getAllGoogleApiBooks(){
		try {
			log.info("GoogleApiBookController:: searching for all the google api books books ");
			List<GoogleApiBook> allGoogleAPIBooks = this.googleApiBookService.getAllGoogleApiBooks();
			
			if(allGoogleAPIBooks.size() == 0) {
				throw new GoogleApiBookNotFoundException("No Google API books available in the system");
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(allGoogleAPIBooks);
			
		}catch (Exception e) {
			
			log.error("Exception occured while fetching google api books!!!");
			e.printStackTrace();
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//getting one book
	@GetMapping(value = "/{googleApiBookId}", produces= {"application/json","application/xml"})
	public ResponseEntity<GoogleApiBook> getOneBook(@PathVariable String googleApiBookId){
		try {
			log.info("GoogleApiBookController:: searching for the book with id : " + googleApiBookId);
			
			GoogleApiBook fetchedBook = this.googleApiBookService.getGoogleApiBookById(googleApiBookId);
			
			return ResponseEntity.status(HttpStatus.OK).body(fetchedBook);
		}catch(Exception e) {
			log.error("Exception occured while fetching google api book for given id " + googleApiBookId);
			e.printStackTrace();
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//adding a book
	@PostMapping(value = "/add",
			produces= {"application/json","application/xml"},
			consumes= {"application/json","application/xml"})
	public ResponseEntity<GoogleApiBook> addBook(@RequestBody GoogleApiBook googleApiBook){
		try {
			log.info("BookController: adding the book...");
			GoogleApiBook addedBook = this.googleApiBookService.addGoogleApiBook(googleApiBook);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
		}catch(Exception e) {
			log.error("error in adding the google api book");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

}
