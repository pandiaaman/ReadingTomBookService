package com.readingTom.bookService.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;

public interface BookCategoryRepository extends PagingAndSortingRepository<BookCategory, String>,JpaRepository<BookCategory, String> {

	//custom finder methods : MAGIC
	Optional<BookCategory> findByCategoryName(String categoryName);
}
