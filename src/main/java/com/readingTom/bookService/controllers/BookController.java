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

import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.services.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	public BookController(BookService bookService) {
		super();
		log.info("inside book controller...");
		this.bookService = bookService;
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
	
	//add book
	@PostMapping(value = "/add",
			produces= {"application/json","application/xml"},
			consumes= {"application/json","application/xml"})
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		try {
			log.info("BookController: adding the book...");
			Book addedBook = this.bookService.addBook(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
		}catch(Exception e) {
			log.error("error in adding the book");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		 
	}
}
