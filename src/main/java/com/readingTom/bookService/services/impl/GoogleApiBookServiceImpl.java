package com.readingTom.bookService.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.readingTom.bookService.customException.GoogleApiBookNotFoundException;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.repositories.GoogleApiBookRepository;
import com.readingTom.bookService.services.GoogleApiBookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GoogleApiBookServiceImpl implements GoogleApiBookService {

	private GoogleApiBookRepository googleApiBookRepository;
	
	public GoogleApiBookServiceImpl(GoogleApiBookRepository googleApiBookRepository) {
		super();
		log.info("entering googleApiBookService...");
		this.googleApiBookRepository = googleApiBookRepository;
	}
	
	@Override
	public List<GoogleApiBook> getAllGoogleApiBooks() {
		log.info("getting all the google api books...");
		List<GoogleApiBook> fetchedBooks = this.googleApiBookRepository.findAll();
		return fetchedBooks;
	}

	@Override
	public GoogleApiBook getGoogleApiBookById(String googleApiBookId) {
		log.info("getting a google api book with id " + googleApiBookId);
		GoogleApiBook fetchedBook = this.googleApiBookRepository.findById(googleApiBookId).orElseThrow(() -> new GoogleApiBookNotFoundException("Google API BOOK with id " + googleApiBookId + " not found in the system!!!"));
		return fetchedBook;
	}
	
	
	@Override
	public boolean checkGoogleApiBookById(String googleApiBookId) {
		log.info("getting a google api book with id " + googleApiBookId);
	    return this.googleApiBookRepository.existsById(googleApiBookId);
	}
	

	@Override
	public GoogleApiBook addGoogleApiBook(GoogleApiBook book) {
		log.info("adding a google api book");
		GoogleApiBook addedBook = this.googleApiBookRepository.save(book);
		return addedBook;
	}

	@Override
	public List<Book> getAllBooksForGivenGoogleApiBook(String googleApiBookid) {
		
		log.info("getting all the locally uploaded books for a given book id");
		
		List<Book> fetchedBooks = new ArrayList<>();
		
		try {
			GoogleApiBook googleApiBook = getGoogleApiBookById(googleApiBookid);
			
			fetchedBooks = googleApiBook.getUploadedBooksListForThisGoogleApiBook();
		}catch(Exception e) {
			log.error("ERROR: no google api book found by id : " + googleApiBookid);
		}
		
		 
		
		return fetchedBooks;
	}

}
