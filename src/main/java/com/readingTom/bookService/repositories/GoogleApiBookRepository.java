package com.readingTom.bookService.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.readingTom.bookService.entities.GoogleApiBook;

public interface GoogleApiBookRepository extends PagingAndSortingRepository<GoogleApiBook, String>,JpaRepository<GoogleApiBook, String> {

//	Page<GoogleApiBook> findGoogleApiBooksByBookCategorysId(String bookCategoryId);
//	
//	Page<GoogleApiBook> findGoogleApiBooksByBookAuthorsId(String bookAuthorId);
}
