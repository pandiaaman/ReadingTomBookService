package com.readingTom.bookService.controllers;

import java.util.ArrayList;
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

import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.customException.GoogleApiBookNotFoundException;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.dto.GoogleApiBookResponseDTO;
import com.readingTom.bookService.dtoMappings.DTOMappings;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.services.GoogleApiBookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/googleapibook")
public class GoogleApiBookController {
	
	@Autowired
	private GoogleApiBookService googleApiBookService;
	
	@Autowired
	private DTOMappings dtoMappings;

	public GoogleApiBookController(GoogleApiBookService googleApiBookService) {
		super();
		log.info("inside GoogleApiBookCOntroller...");
		this.googleApiBookService = googleApiBookService;
	}
	
	//getting all books
	@GetMapping(value = "/all", produces= {"application/json","application/xml"})
	public ResponseEntity<List<GoogleApiBookResponseDTO>> getAllGoogleApiBooks(){
		try {
			log.info("GoogleApiBookController:: searching for all the google api books books ");
			
			List<GoogleApiBookResponseDTO> outputGoogleApiBooksList = new ArrayList<>();
			
			List<GoogleApiBook> allGoogleAPIBooks = this.googleApiBookService.getAllGoogleApiBooks();
			
			if(allGoogleAPIBooks.size() == 0) {
				throw new GoogleApiBookNotFoundException("No Google API books available in the system");
			}
			
			for(GoogleApiBook googleApiBook : allGoogleAPIBooks) {
				GoogleApiBookResponseDTO currentGoogleApiBook = dtoMappings.mapGoogleApiBookToGoogleApiBookResponseDTO(googleApiBook);
				outputGoogleApiBooksList.add(currentGoogleApiBook);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outputGoogleApiBooksList);
			
		}catch (Exception e) {
			
			log.error("Exception occured while fetching google api books!!!");
			e.printStackTrace();
			
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//getting one book
	@GetMapping(value = "/{googleApiBookId}", produces= {"application/json","application/xml"})
	public ResponseEntity<GoogleApiBookResponseDTO> getOneBook(@PathVariable String googleApiBookId){
		try {
			log.info("GoogleApiBookController:: searching for the book with id : " + googleApiBookId);
			
			GoogleApiBook fetchedBook = this.googleApiBookService.getGoogleApiBookById(googleApiBookId);
			
			GoogleApiBookResponseDTO outgoingGoogleApiBook = dtoMappings.mapGoogleApiBookToGoogleApiBookResponseDTO(fetchedBook);
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingGoogleApiBook);
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
			log.info("GoogleApiBookController: adding the book...");
			GoogleApiBook addedBook = this.googleApiBookService.addGoogleApiBook(googleApiBook);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
		}catch(Exception e) {
			log.error("error in adding the google api book");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	//additional methods 
	@GetMapping(value = "/{googleApiBookId}/books", produces= {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllBooksForGivenGoogleApiBook(@PathVariable String googleApiBookId){
		try {
			
			log.info("GoogleApiBookController :: getAllBooksForGivenGoogleApiBook :: getting all uploaded books for given google book id : " + googleApiBookId);
			
			List<Book> fetchedBooks = this.googleApiBookService.getAllBooksForGivenGoogleApiBook(googleApiBookId);
			
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			if(fetchedBooks.size() > 0) {
				for(Book book : fetchedBooks) {
					BookResponseDTO currentBook = dtoMappings.mapBookToBookResponseDto(book);
					outgoingBooks.add(currentBook);
				}
			}else {
				throw new BookNotFoundException("No books in the local system for id: " + googleApiBookId);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
			
		}catch(Exception e) {
			log.error("Error in getting all the uploaded books for the given google api book id " + googleApiBookId);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	

}
