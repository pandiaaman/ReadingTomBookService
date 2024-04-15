package com.readingTom.bookService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.readingTom.bookService.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{

}
