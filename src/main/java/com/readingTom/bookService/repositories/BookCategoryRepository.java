package com.readingTom.bookService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTom.bookService.entities.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, String> {

}
