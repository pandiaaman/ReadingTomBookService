package com.readingTom.bookService.services;

import java.util.List;
import java.util.Optional;


import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;


public interface BookCategoryService {

	//get all book categories
	public List<BookCategory> getAllBookCategories();
		
	//get category by id
	public BookCategory getBookCategoriesById(String categoryId);
		
	//create/add a category
	public BookCategory addBookCategory(BookCategory bookCategory);
	
	//find the category by name //custom finder methods
	public BookCategory findByCategoryName(String categoryName);
	
	//find googleApiBooks by category name
	public List<GoogleApiBook> getAllGoogleApiBooksUploadedForThisCategoryName(String categoryName);
	
	//find googleApiBooks by category id
	public List<GoogleApiBook> getAllGoogleApiBooksUploadedForThisCategoryId(String categoryId);
}
