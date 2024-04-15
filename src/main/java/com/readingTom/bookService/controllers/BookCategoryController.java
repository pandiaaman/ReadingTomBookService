package com.readingTom.bookService.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookServices.BookCategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bookcategory")
public class BookCategoryController {

	private BookCategoryService bookCategoryService;

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
		public ResponseEntity<BookCategory> getOneAuthor(@PathVariable String categoryId) {
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
	
	
}
