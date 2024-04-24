package com.readingTom.bookService.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.readingTom.bookService.entities.Book;


public interface BookService {

	//get all books
	public List<Book> getAllBooks(Pageable pageable);
	
	//get book by id
	public Book getBookById(String bookId);
	
	public List<Book> getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String ownerId, boolean isBookForRent, Pageable pageable);

	public List<Book> getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String ownerId, boolean isBookForSwap, Pageable pageable);

	public List<Book> getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String googleApiBookId, boolean isBookForRent, Pageable pageable);
	
	public List<Book> getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String googleApiBookId, boolean isBookForSwap, Pageable pageable);

	//create/add a book
	public Book addBook(Book book);
}
