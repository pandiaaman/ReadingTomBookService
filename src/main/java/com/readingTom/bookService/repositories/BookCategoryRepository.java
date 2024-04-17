package com.readingTom.bookService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.readingTom.bookService.entities.BookCategory;

public interface BookCategoryRepository extends JpaRepository<BookCategory, String> {

	//custom finder methods : MAGIC
	Optional<BookCategory> findByCategoryName(String categoryName);
}
