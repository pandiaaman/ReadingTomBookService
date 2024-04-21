package com.readingTom.bookService.dto;

import java.time.LocalDateTime;

import com.readingTom.bookService.entities.GoogleApiBook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookResponseDTO {

	private String bookId;
	
	private String googleApiBookId;
	
	private GoogleApiBookResponseDTO googleApiBook; 
	
	private String bookOwnerId;
	
	private String bookOwner; 
	
	private boolean isBookAvailable;
	
	private boolean isBookSwapped;
	
	private boolean isBookRentedAtThisTime;
	
	private boolean isBookForRent;
	
	private boolean isBookForSwap;
	
	private int totalOngoingInteractionsForThisBook;
	
	private LocalDateTime bookCreatedAt;
	
	private LocalDateTime bookUpdatedAt;
	
	private String userUploadedBookImage1TemporaryUrl;
	
	private String userUploadedBookImage2TemporaryUrl;
	
	private String userUploadedBookBarcodeImageTemporaryUrl;

	private String userUploadedBookImage1PermanentUrl;
	
	private String userUploadedBookImage2PermanentUrl;
	
	private String userUploadedBookBarcodeImagePermanentUrl;
	
	private String userUploadedBookBarcodeIsbnValue;
	
	private String userUploadedBookDetails;
	
	private boolean isBookVerified;
}
