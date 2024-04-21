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
public class BookAuthorResponseDTO {

	private String authorName;
	
	private int totalGoogleApiBooksUploadedForThisAuthor;
	
	private int totalBooksUploadedForThisAuthor;
	
	private int rentalBooksUploadedForThisAuthor;
	
	private int swapBooksUploadedForThisAuthor;
	
	private int ongoingInteractionsForThisAuthor;
	
	private int totalFulfilledInteractionForThisAuthor;	
	
	private LocalDateTime bookAuthorCreatedAt;
	
	private LocalDateTime bookAuthorUpdatedAt;
}
