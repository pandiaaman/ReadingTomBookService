package com.readingTom.bookServices.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readingTom.bookService.customException.AuthorNotFoundException;
import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.repositories.BookAuthorRepository;
import com.readingTom.bookServices.BookAuthorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookAuthorServiceImpl implements BookAuthorService{

	private BookAuthorRepository bookAuthorRepository;
	
	public BookAuthorServiceImpl(BookAuthorRepository bookAuthorRepository) {
		super();
		log.info("inside BookAuthorServiceImpl...");
		this.bookAuthorRepository = bookAuthorRepository;
	}
	
	@Override
	public List<BookAuthor> getAllBookAuthors() {
		log.info("BookAuthorService:: fetching all book authors...");
		
		List<BookAuthor> bookAuthors = this.bookAuthorRepository.findAll();
		return bookAuthors;
	}

	@Override
	public BookAuthor getBookAuthorsById(String authorId) {
		log.info("BookAuthorService:: fetching one book authors with id " + authorId);
		
		BookAuthor bookAuthor = this.bookAuthorRepository.findById(authorId).orElseThrow(() -> new AuthorNotFoundException("author with id " + authorId + " not found in the system"));
		return bookAuthor;
	}

	@Override
	public BookAuthor addBookAuthor(BookAuthor bookAuthor) {
		log.info("BookAuthorService:: adding book author...");
		
		BookAuthor savedAuthor = this.bookAuthorRepository.save(bookAuthor);
		
		return savedAuthor;
	}

}
