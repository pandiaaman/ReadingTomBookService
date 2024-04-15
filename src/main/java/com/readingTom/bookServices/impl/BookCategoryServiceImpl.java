package com.readingTom.bookServices.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.readingTom.bookService.customException.CategoryNotFoundException;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.repositories.BookCategoryRepository;
import com.readingTom.bookServices.BookCategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BookCategoryServiceImpl implements BookCategoryService{

	private BookCategoryRepository bookCategoryRepository;
	
	public BookCategoryServiceImpl(BookCategoryRepository bookCategoryRepository) {
		super();
		log.info("entering bookCategoryService...");
		this.bookCategoryRepository = bookCategoryRepository;
	}
	
	@Override
	public List<BookCategory> getAllBookCategories() {
		log.info("getting all book categories...");
		List<BookCategory> fetchedCategories = this.bookCategoryRepository.findAll();
		return fetchedCategories;
	}

	@Override
	public BookCategory getBookCategoriesById(String categoryId) {
		log.info("getting a book category with id: " + categoryId);
		BookCategory category = this.bookCategoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("book category " + categoryId + " not found in system"));
		return category;
	}

	@Override
	public BookCategory addBookCategory(BookCategory bookCategory) {
		log.info("adding a book category...");
		BookCategory addedCategory = this.bookCategoryRepository.save(bookCategory);
		return addedCategory;
	}

}
