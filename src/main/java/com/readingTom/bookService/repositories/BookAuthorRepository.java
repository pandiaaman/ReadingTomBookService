package com.readingTom.bookService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.BookAuthor;

public interface BookAuthorRepository extends PagingAndSortingRepository<BookAuthor, String>,JpaRepository<BookAuthor, String>{

	Optional<BookAuthor> findByAuthorName(String authorName); 

}
