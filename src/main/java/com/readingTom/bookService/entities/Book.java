package com.readingTom.bookService.entities;


import org.hibernate.annotations.ColumnDefault;

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
	
	//multiple books in the local system will be associated to one particular book from google books api
	//process will be to get the book details and then first call the google api, get data and create the googleAPiBook object(if not existing already) and then store it here
	//many to one
	@ManyToOne
	@JoinColumn(name = "google_api_book_reference")
	private GoogleApiBook googleApiBook; //we first create the googleAPIBook object in the table and then use that reference
	
	
	//coming from the user service brining in the user id who uploaded the book
	@Column(name = "book_owner_reference")
	private String bookOwner; //TODO String will be converted to the bookOwner (Type User) class reference
	
	@Column(name = "is_book_available")
	@ColumnDefault("true")
	private boolean isBookAvailable;
	
	@Column(name = "is_book_swapped")
	@ColumnDefault("false")
	private boolean isBookSwapped;
	
	@Column(name = "is_book_rented_at_this_time")
	@ColumnDefault("false")
	private boolean isBookRentedAtThisTime;
	
	@Column(name = "is_book_for_rent")
	@ColumnDefault("false")
	private boolean isBookForRent;
	
	@Column(name = "is_book_for_swap")
	@ColumnDefault("false")
	private boolean isBookForSwap;
	
	@Column(name = "is_book_for_rent_and_swap")
	@ColumnDefault("false")
	private boolean isBookForRentAndSwap;
	
	@Column(name = "total_ongoing_interactions_for_this_book")
	private int totalOngoingInteractionsForThisBook;
}
