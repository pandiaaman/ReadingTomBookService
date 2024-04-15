package com.readingTom.bookService.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "book_category_table")
public class BookCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "category_name")
	private String categoryName;
	
	//many to many
	@ManyToMany(mappedBy = "googleApiBookCategories")
	@Column(name = "google_api_book_reference")
	private List<GoogleApiBook> googleApiBook;
	
	@Column(name = "total_google_api_books_uploaded_for_this_category")
	private int totalGoogleApiBooksUploadedForThisCategory;
	
	@Column(name = "total_books_uploaded_for_this_category")
	private int totalBooksUploadedForThisCategory;
	
	@Column(name = "rental_books_uploaded_for_this_category")
	private int rentalBooksUploadedForThisCategory;
	
	@Column(name = "swap_books_uploaded_for_this_category")
	private int swapBooksUploadedForThisCategory;
	
	@Column(name = "ongoing_interactions_for_this_category")
	private int ongoingInteractionsForThisCategory;
	
	@Column(name = "total_fulfilled_interactions_for_this_category")
	private int totalFulfilledInteractionForThisCategory;
}