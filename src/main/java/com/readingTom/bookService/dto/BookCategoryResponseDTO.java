package com.readingTom.bookService.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookCategoryResponseDTO {

	private String categoryName;
	
	private int totalGoogleApiBooksUploadedForThisCategory;

	private int totalBooksUploadedForThisCategory;
	
	private int rentalBooksUploadedForThisCategory;
	
	private int swapBooksUploadedForThisCategory;
	
	private int ongoingInteractionsForThisCategory;
	
	private int totalFulfilledInteractionForThisCategory;
	
	private LocalDateTime bookCategoryCreatedAt;
	
	private LocalDateTime bookCategoryUpdatedAt;
}
