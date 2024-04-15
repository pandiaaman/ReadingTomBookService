package com.readingTom.bookServices;

import java.util.List;

import com.readingTom.bookService.entities.GoogleApiBook;


public interface GoogleApiBookService {

	//get all google api books
	public List<GoogleApiBook> getAllGoogleApiBooks();
		
	//get google api book by id
	public GoogleApiBook getGoogleApiBookById(String googleApiBookId);
		
	//create/add a google api book
	public GoogleApiBook addGoogleApiBook(GoogleApiBook book);
}
