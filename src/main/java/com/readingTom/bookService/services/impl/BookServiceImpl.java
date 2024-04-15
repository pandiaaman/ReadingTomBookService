package com.readingTom.bookService.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Book> getAllBooks() {
		log.info("BookService:: fetching all books...");
		
		List<Book> books = this.bookRepository.findAll();
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
