package com.readingTom.bookService.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GoogleApiBookResponseDTO {

	private String googleApiBookId;
	
	private String googleApiBookTitle;
	
	private String googleApiBookSubTitle;
	
	//contains id of all books that are uploaded for this google api book
	private List<String> booksIdUploadedForThisGoogleApiBook;
	
	private String googleApiBookThumbnailUrl;

	private String googleApiBookSmallThumbnailUrl;
	
	private String googleApiBookPublisher;
	
	private String googleApiBookPublishedDate;
	
	private int googleApiBookPageCount;
	
	private List<BookCategoryResponseDTO> googleApiBookCategories;
	
	private List<BookAuthorResponseDTO> googleApiBookAuthors;
	
	private String googleApiBookLanguage;
	
	private String googleApiBookIsbn10;
	
	private String googleApiBookIsbn13;
	
	private double googleApiBookRetailPrice;
	
	private String googleApiBookRetailPriceCurrencyCode;
	
	private int totalBooksUploadedForThisGoogleApiBook;
	
	private int rentalBooksUploadedForThisGoogleApiBook;
	
	private int swapBooksUploadedForThisGoogleApiBook;
	
	private int ongoingInteractionsForThisGoogleApiBook;
	
	private int totalFulfilledInteractionForThisGoogleApiBook;
	
	private LocalDateTime googleApiBookCreatedAt;
	
	private LocalDateTime googleApiBookUpdatedAt;
}
