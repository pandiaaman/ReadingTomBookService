package com.readingTom.bookServices;

import java.util.List;

import com.readingTom.bookService.entities.BookCategory;

public interface BookCategoryService {

	//get all book categories
	public List<BookCategory> getAllBookCategories();
		
	//get category by id
	public BookCategory getBookCategoriesById(String categoryId);
		
	//create/add a category
	public BookCategory addBookCategory(BookCategory bookCategory);
}
