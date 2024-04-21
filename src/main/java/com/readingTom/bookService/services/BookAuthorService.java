package com.readingTom.bookService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.GoogleApiBook;

public interface BookAuthorService {

	//get all authors
	public List<BookAuthor> getAllBookAuthors();
		
	//get author by id
	public BookAuthor getBookAuthorsById(String bookId);
		
	//create/add a author
	public BookAuthor addBookAuthor(BookAuthor book);

	public BookAuthor findByAuthorName(String authorName); 
	
	public List<GoogleApiBook> getAllGoogleApiBooksUploadedForThisAuthor(String authorName);
}