package com.readingTom.bookServices;

import java.util.List;

import com.readingTom.bookService.entities.Book;

public interface BookService {

	//get all books
	public List<Book> getAllBooks();
	
	//get book by id
	public Book getBookById(String bookId);
	
	//create/add a book
	public Book addBook(Book book);
}
