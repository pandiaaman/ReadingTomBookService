package com.readingTom.bookService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTom.bookService.entities.BookAuthor;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, String>{

}
