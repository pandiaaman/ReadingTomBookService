package com.readingTom.bookService.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.repositories.BookRepository;
import com.readingTom.bookService.services.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	public BookServiceImpl(BookRepository bookRepository) {
		super();
		log.info("inside BookServiceImpl...");
		this.bookRepository = bookRepository;
	}
	
	@Override
	public List<Book> getAllBooks(Pageable pageable) {
		log.info("BookService:: fetching all books...");
		
		//this handles the pagination of the results  (NOTICE how we have added PagingAndSortingRepository to BookRepository)
		Page<Book> booksPage = this.bookRepository.findAll(pageable);
		List<Book> books = booksPage.getContent();
		return books;
	}
	
	//
	public List<Book> getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String ownerId, boolean isBookForRent, Pageable pageable){
		log.info("BookService :: getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified...");
		
		Page<Book> booksPage = this.bookRepository.findByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(ownerId, true, isBookForRent, true, pageable);
		List<Book> books = booksPage.getContent();
		
		return books;
	
	}
	
	//
	public List<Book> getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String ownerId, boolean isBookForSwap, Pageable pageable){
		log.info("BookService :: getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified...");
		
		Page<Book> booksPage = this.bookRepository.findByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(ownerId, true, isBookForSwap, true, pageable);
		List<Book> books = booksPage.getContent();
		
		return books;
	
	}
	
	//
	public List<Book> getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String googleApiBookId, boolean isBookForRent, Pageable pageable){
		log.info("BookService :: getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified...");
		
		Page<Book> booksPage = this.bookRepository.findByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(googleApiBookId, true, isBookForRent, true, pageable);
		List<Book> books = booksPage.getContent();
		
		return books;
	
	}
	
	public List<Book> getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String googleApiBookId, boolean isBookForSwap, Pageable pageable){
		log.info("BookService :: getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified...");
		
		Page<Book> booksPage = this.bookRepository.findByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(googleApiBookId, true, isBookForSwap, true, pageable);
		List<Book> books = booksPage.getContent();
		
		return books;
	
	}

	@Override
	public Book getBookById(String bookId) {
		log.info("BookService:: getting book with book id: " + bookId);
		
		Book book = this.bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("book id " + bookId + " Not found!!"));
		return book;
	}

	@Override
	public Book addBook(Book book) {
		log.info("BookService:: adding book to the system...");
		Book addedBook = this.bookRepository.save(book);
		return addedBook;
	}

}
