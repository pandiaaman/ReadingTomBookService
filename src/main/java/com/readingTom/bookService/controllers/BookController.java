package com.readingTom.bookService.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingTom.bookService.BusinessLogic.BookAddBO;
import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.repositories.GoogleApiBookRepository;
import com.readingTom.bookService.services.BookService;
import com.readingTom.bookService.services.GoogleApiBookService;
import com.readingTom.bookService.services.impl.GoogleApiBookServiceImpl;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBook;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookImageLinks;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookVolumeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	public BookController() {
		super();
		log.info("inside book controller...");
	}
	
	//TODO: add get book by different field methods
	
	//getting books
	@GetMapping(value = "/all", produces= {"application/json","application/xml"})
	public ResponseEntity<List<Book>> getAllBooks(){
		try {
			log.info("BookController: getting all books...");
			List<Book> allBooks = this.bookService.getAllBooks();
			
			//if books fetched array is empty
			if(allBooks.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			return ResponseEntity.status(HttpStatus.OK).body(allBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	//get one book
	@GetMapping(value = "/{bookId}", produces= {"application/json","application/xml"})
	public ResponseEntity<Book> getOneBook(@PathVariable String bookId) {
		try {
			log.info("BookController: getting book with id " + bookId);
			Book book = this.bookService.getBookById(bookId);
			
			return ResponseEntity.status(HttpStatus.OK).body(book);
		}catch(Exception e) {
			log.error("error in getting the book");
			e.printStackTrace(); //TODO we can throw the book not found exception here (custom exception)
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	@Autowired
	private GoogleApiBookService googleApiBookService;
	
	@Autowired
	BookAddBO bookAdditionBusinessObject;
	
	//add book
	@PostMapping(value = "/add",
			produces= {"application/json","application/xml"},
			consumes= {"application/json","application/xml"})
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		try {
			log.info("BookController: adding the book...");
			
			//BusinessLogic : TODO: later move these to different methods
			/*
			 * on add book request we take googleapibookid and call google api and fetch the data
			 * this request can be made asynchronously for which we can use web client
			 * using the data we first create an object of googleapibook
			 * inside googleapibook we have categories and authors
			 * while creating that object, we take values from the list and create object for categories and authors
			 * finally when all are added we save the googleapibook and then we save the book
			 */
			String incomingGoogleApiBookId = book.getGoogleApiBookId();
			boolean isBookForRent = book.isBookForRent();
			boolean isBookForSwap = book.isBookForSwap();
			boolean isBookForRentAndSwap = book.isBookForRentAndSwap();
			
			
			log.info("****values coming in the system ::: incomingGoogleApiBookId " + incomingGoogleApiBookId);
			log.info("**** isBookForRent " + isBookForRent + " :: isBookForSwap " + isBookForSwap + " :: isBookForRentAndSwap " + isBookForRentAndSwap ); 
			
			GoogleApiBook googleApiBook = bookAdditionBusinessObject.addingOrUpdatingGoogleApiBook(incomingGoogleApiBookId, isBookForRent, isBookForSwap, isBookForRentAndSwap);
			
			
		    book.setGoogleApiBook(googleApiBook);
		    book.setBookAvailable(true);
		    book.setBookRentedAtThisTime(false);
		    book.setBookSwapped(false);
		    book.setTotalOngoingInteractionsForThisBook(0);
		    
		    log.info("****************** book :: " + book);
		    
		    this.bookService.addBook(book);
		    
//			Book addedBook = this.bookService.addBook(book);
//			return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
		    
		    return ResponseEntity.status(HttpStatus.CREATED).body(null);
		}catch(Exception e) {
			log.error("error in adding the book");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		 
	}
	
}