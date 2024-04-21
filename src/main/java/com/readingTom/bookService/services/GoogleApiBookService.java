package com.readingTom.bookService.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;


public interface GoogleApiBookService {

	//get all google api books
	public List<GoogleApiBook> getAllGoogleApiBooks();
		
	//get google api book by id
	public GoogleApiBook getGoogleApiBookById(String googleApiBookId);
	
	public boolean checkGoogleApiBookById(String googleApiBookId);
		
	//create/add a google api book
	public GoogleApiBook addGoogleApiBook(GoogleApiBook book);
	
	public List<Book> getAllBooksForGivenGoogleApiBook(String googleApiBookid);
}
