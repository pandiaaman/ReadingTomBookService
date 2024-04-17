package com.readingTom.bookService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTom.bookService.entities.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, String>{

	Optional<BookAuthor> findByAuthorName(String authorName); 

}
