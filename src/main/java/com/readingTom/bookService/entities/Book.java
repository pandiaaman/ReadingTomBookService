package com.readingTom.bookService.entities;


import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * the book object will be created every time a user uploads a book
 * the uploaded book will have the google api book id associated with it along with the data entered by the user
 * if the google api associated with book exists in the googleapibook (controller) table then we update the number of books attached to that id
 * else we add a new row in the googlebookapi table with the required data like title, isbn etc to the table
 * along with this we check for the category coming in from the googleapi, if it exists in category table then we add 1 to total no
 * else we add a new category
 * for user entered category, if enetered any, then we increase the number to 1 in category table and pass the id of category to the book table
 * 
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "book_table")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "book_id")
	private String bookId;
	
	//we can't send the book object from frontend so we rely on the id and then create the required googleapibook object and add it by ourselves
	@Column(name = "google_api_book_id")
	private String googleApiBookId;
	
	//multiple books in the local system will be associated to one particular book from google books api
	//process will be to get the book details and then first call the google api, get data and create the googleAPiBook object(if not existing already) and then store it here
	//many to one
	@JsonIgnore //so that complete details do NOT show up in GET request
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "google_api_book_reference")
	private GoogleApiBook googleApiBook; //we first create the googleAPIBook object in the table and then use that reference
	
	
	//coming from the user service brining in the user id who uploaded the book
	@Column(name = "book_owner_reference_id")
	private String bookOwnerId; //TODO String will be converted to the bookOwner (Type User) class reference
	
	@JsonIgnore
	@Column(name = "book_owner_reference")
	private String bookOwner; 
	
	@Column(name = "is_book_available", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
	@ColumnDefault("true")
	@JsonProperty
	private boolean isBookAvailable;
	
	@Column(name = "is_book_swapped", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	@ColumnDefault("false")
	@JsonProperty
	private boolean isBookSwapped;
	
	@Column(name = "is_book_rented_at_this_time", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	@ColumnDefault("false")
	@JsonProperty
	private boolean isBookRentedAtThisTime;
	
	@Column(name = "is_book_for_rent", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	@ColumnDefault("false")
	@JsonProperty
	private boolean isBookForRent;
	
	@Column(name = "is_book_for_swap", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	@ColumnDefault("false")
	@JsonProperty
	private boolean isBookForSwap;
	
	@Column(name = "total_ongoing_interactions_for_this_book", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int totalOngoingInteractionsForThisBook;
	
	@CreationTimestamp
	@Column(name = "book_created_at", nullable = false, updatable = false)
	private LocalDateTime bookCreatedAt;
	
	@UpdateTimestamp
	@Column(name = "book_updated_at")
	private LocalDateTime bookUpdatedAt;
	
	
	@Column(name = "user_uploaded_book_image_1_temporary_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookImage1TemporaryUrl;
	
	@Column(name = "user_uploaded_book_image_2_temporary_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookImage2TemporaryUrl;
	
	@Column(name = "user_uploaded_book_barcode_image_temporary_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookBarcodeImageTemporaryUrl;

	@Column(name = "user_uploaded_book_image_1_permanent_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookImage1PermanentUrl;
	
	@Column(name = "user_uploaded_book_image_2_permanent_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookImage2PermanentUrl;
	
	@Column(name = "user_uploaded_book_barcode_image_permanent_url", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookBarcodeImagePermanentUrl;
	
	//user can upload either bar code or isbn value of the book: front end will only allow any one
	@Column(name = "user_uploaded_book_barcode_isbn_value", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookBarcodeIsbnValue;
	
	@Column(name = "user_uploaded_book_details", columnDefinition = "VARCHAR(512) DEFAULT ''")
	private String userUploadedBookDetails;
	
	
	//this column is updated by Kafka event once the images are verified by the image verification service
	@Column(name = "is_book_verified", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	@ColumnDefault("false")
	@JsonProperty
	private boolean isBookVerified;
	
}
