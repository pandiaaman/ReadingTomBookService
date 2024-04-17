package com.readingTom.bookService.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * To add the google book api we need to send a json object to the backend if the id already does not exist
 * for now, i think we should use graphql as it provides a proper template and since we are not using all the data
 * TODO
 * even when we are trying to get the number of books stored for a particular google book api, we should use 
 * graphQL
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "google_api_book_table")
public class GoogleApiBook {

	//TODO: test that we can add the id from the json data coming from front end
	@Id //No generated values here as we will be using the same id as present in the google books api
	@Column(name = "google_api_book_id")
	private String googleApiBookId;
	
	@Column(name = "google_api_book_title")
	private String googleApiBookTitle;
	
	@Column(name = "google_api_book_subtitle")
	private String googleApiBookSubTitle;
	
	@Column(name = "google_api_book_publisher")
	private String googleApiBookPublisher;
	
	@Column(name = "google_api_book_publish_date")
	private String googleApiBookPublishedDate;
	
	@Column(name = "google_api_book_page_count")
	private int googleApiBookPageCount;
	
	//when the categories come in, we take the array of the string, select each value and check if they exist in BookCategory table
	//if the category exists in category table, we add a reference of that category in this column
	//if it doesn't exist then we create a add to the category table and save that reference to this column
	//many to many relationship
	@ManyToMany(cascade = CascadeType.ALL) //now we dont  need to save the category one separately, the moment we save google book api, the category will automatically be saved
	@Column(name = "google_api_book_categories")
	private List<BookCategory> googleApiBookCategories = new ArrayList<>(); //TODO: check if we need to initiate this as ArrayList();
	
	//Note: the class in which we use cascase.ALL, we only need to save the object of that class in JPA
	 
	//many to many
	@ManyToMany(cascade = CascadeType.ALL) //table containing the mapping will be mapped by the bookAuthor column
	@Column(name = "google_api_book_authors")
	private List<BookAuthor> googleApiBookAuthors = new ArrayList<>();
	
	@Column(name = "google_api_book_language")
	private String googleApiBookLanguage;
	
	@Column(name = "google_api_book_isbn_10")
	private String googleApiBookIsbn10;
	
	@Column(name = "google_api_book_isbn_13")
	private String googleApiBookIsbn13;
	
	@Column(name = "google_api_book_retail_price")
	private double googleApiBookRetailPrice;
	
	@Column(name = "google_api_book_retail_price_currency_code")
	private String googleApiBookRetailPriceCurrencyCode;
	
	//one google api book will be having many books locally uploaded in the system
	//one to many
	@OneToMany(mappedBy = "googleApiBook") //cascade: if we remove a google api book, all associated books will be removed along with it
	private List<Book> uploadedBooksListForThisGoogleApiBook;
	
	@Column(name = "total_books_uploaded_for_this_google_api_book")
	private int totalBooksUploadedForThisGoogleApiBook;
	
	@Column(name = "rental_books_uploaded_for_this_google_api_book")
	private int rentalBooksUploadedForThisGoogleApiBook;
	
	@Column(name = "swap_books_uploaded_for_this_google_api_book")
	private int swapBooksUploadedForThisGoogleApiBook;
	
	@Column(name = "ongoing_interactions_for_this_google_api_book")
	private int ongoingInteractionsForThisGoogleApiBook;
	
	@Column(name = "total_fulfilled_interactions_for_this_google_api_book")
	private int totalFulfilledInteractionForThisGoogleApiBook;
}
