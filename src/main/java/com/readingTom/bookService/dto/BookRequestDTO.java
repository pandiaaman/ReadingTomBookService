package com.readingTom.bookService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
  
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookRequestDTO {

	private String bookOwnerId;
	
	private String googleApiBookId;
	@JsonProperty
	private boolean isBookForRent;
	@JsonProperty
	private boolean isBookForSwap;
	
	private int bookRentalPricePerDay;
	
	private String bookRentalPricePerDayCurrency;
	
	private String userUploadedBookImage1TemporaryUrl;
	
	private String userUploadedBookImage2TemporaryUrl;
	
	private String userUploadedBookBarcodeImageTemporaryUrl;
	
	private String userUploadedBookDetails;
}
