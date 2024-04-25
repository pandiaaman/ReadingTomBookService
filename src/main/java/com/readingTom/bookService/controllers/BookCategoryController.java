package com.readingTom.bookService.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.readingTom.bookService.dtoMappings.DTOMappings;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.services.BookCategoryService;
import com.readingTom.bookService.services.GoogleApiBookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bookcategory")
public class BookCategoryController {

	@Autowired
	private BookCategoryService bookCategoryService;
	
	@Autowired
	@Lazy
	private GoogleApiBookService googleApiBookService;
	
	@Autowired
	@Lazy
	private DTOMappings dtoMappings;

	public BookCategoryController(BookCategoryService bookCategoryService) {
		super();
		log.info("inside BookCategoryController...");
		this.bookCategoryService = bookCategoryService;
	}
	
	//get all categories
		@GetMapping(value = "/all", produces = {"application/json","application/xml"})
		public ResponseEntity<List<BookCategory>> getAllBookCategories(){
			try {
				log.info("BookCategoryController:: inside getAllBookCategories:: getting all categories");
				
				List<BookCategory> fetchedCategories = this.bookCategoryService.getAllBookCategories();
				
				return ResponseEntity.status(HttpStatus.OK).body(fetchedCategories);
				
			}catch(Exception e) {
				log.error("Exception in getting all categories from the system...");
				e.printStackTrace();
			}
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		//get category by id
		@GetMapping(value = "/{categoryId}", produces= {"application/json","application/xml"})
		public ResponseEntity<BookCategory> getOneCategory(@PathVariable String categoryId) {
			try {
				log.info("BookCategoryController:: getOneAuthor:: getting category with id " + categoryId);
				BookCategory category = this.bookCategoryService.getBookCategoriesById(categoryId);
				
				return ResponseEntity.status(HttpStatus.OK).body(category);
			}catch(Exception e) {
				log.error("error in getting the category");
				e.printStackTrace(); //TODO we can throw the author not found exception here (custom exception)
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		//add all categories
		@PostMapping(value = "/add",
				produces= {"application/json","application/xml"},
				consumes= {"application/json","application/xml"})
		public ResponseEntity<BookCategory> addBookCategory(@RequestBody BookCategory bookCategory) {
			try {
				log.info("BookCategoryController:: addBookAuthor:: adding the book...");
				BookCategory addedBookCategory = this.bookCategoryService.addBookCategory(bookCategory);
				return ResponseEntity.status(HttpStatus.CREATED).body(addedBookCategory);
			}catch(Exception e) {
				log.error("error in adding the book category");
				e.printStackTrace(); 
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
			 
		}
	
		
		//other requests
		@GetMapping(value = "byname/googleapibooks", produces= {"application/json","application/xml"})
		public ResponseEntity<List<GoogleApiBook>> getAllGoogleApiBooksUploadedForThisCategoryName(@RequestParam String category){
			try {
				String decodedCategoryName = UriComponentsBuilder.fromPath(category).build().toString();
				
				List<GoogleApiBook> fetchedBooks = this.bookCategoryService.getAllGoogleApiBooksUploadedForThisCategoryName(decodedCategoryName);
				
				return ResponseEntity.status(HttpStatus.OK).body(fetchedBooks);
			}catch(Exception e) {
				log.error("error in getting google api books for the given category name");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		@GetMapping(value = "byid/googleapibooks", produces= {"application/json","application/xml"})
		public ResponseEntity<List<GoogleApiBook>> getAllGoogleApiBooksUploadedForThisCategoryId(@RequestParam String category){
			try {
				String decodedCategoryId = UriComponentsBuilder.fromPath(category).build().toString();
				
				List<GoogleApiBook> fetchedBooks = this.bookCategoryService.getAllGoogleApiBooksUploadedForThisCategoryId(decodedCategoryId);
				
				return ResponseEntity.status(HttpStatus.OK).body(fetchedBooks);
			}catch(Exception e) {
				log.error("error in getting google api books for the given category id");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		@GetMapping(value = "byname/books", produces= {"application/json","application/xml"})
		public ResponseEntity<List<Book>> getAllBooksUploadedForThisCategoryName(@RequestParam String category){
			try {
				log.info("BookCategoryController :: getAllBooksUploadedForThisCategory");
				// Decode the authorName if it's encoded (space is %20 and / is %2F)
		        String decodedCategoryName = UriComponentsBuilder.fromPath(category).build().toString();

				List<GoogleApiBook> fetchedGoogleApiBooks = this.bookCategoryService.getAllGoogleApiBooksUploadedForThisCategoryName(decodedCategoryName);
			
				List<Book> booksResultSet = new ArrayList<>();
				
				for(GoogleApiBook googleApiBook : fetchedGoogleApiBooks) {
					String googleApiBookId = googleApiBook.getGoogleApiBookId();
					
					List<Book> booksForGivenId = googleApiBookService.getAllBooksForGivenGoogleApiBook(googleApiBookId);
					
					booksResultSet.addAll(booksForGivenId);
				}
				
				return ResponseEntity.status(HttpStatus.OK).body(booksResultSet);
			}catch(Exception e) {
				log.error("error in getting google api books for the given category");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		
		
		@GetMapping(value = "byid/books", produces= {"application/json","application/xml"})
		public ResponseEntity<List<Book>> getAllBooksUploadedForThisCategoryId(@RequestParam String category){
			try {
				log.info("BookCategoryController :: getAllBooksUploadedForThisCategory");
				// Decode the authorName if it's encoded (space is %20 and / is %2F)
		        String decodedCategoryId = UriComponentsBuilder.fromPath(category).build().toString();

				List<GoogleApiBook> fetchedGoogleApiBooks = this.bookCategoryService.getAllGoogleApiBooksUploadedForThisCategoryId(decodedCategoryId);
			
				List<Book> booksResultSet = new ArrayList<>();
				
				for(GoogleApiBook googleApiBook : fetchedGoogleApiBooks) {
					String googleApiBookId = googleApiBook.getGoogleApiBookId();
					
					List<Book> booksForGivenId = googleApiBookService.getAllBooksForGivenGoogleApiBook(googleApiBookId);
					
					booksResultSet.addAll(booksForGivenId);
				}
				
				return ResponseEntity.status(HttpStatus.OK).body(booksResultSet);
			}catch(Exception e) {
				log.error("error in getting google api books for the given category");
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	
}
