package com.readingTom.bookService.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "book_author_table")
public class BookAuthor {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "author_id")
	private String authorId;
	
	@Column(name = "author_name")
	private String authorName;
	
	
	//many to many
	@JsonIgnore
	@ManyToMany(mappedBy = "googleApiBookAuthors") //the table that will be created now showing the combination of book with author will be handled by the book table's author value
	@Column(name = "google_api_book_reference")
	private List<GoogleApiBook> googleApiBook;
	
	@Column(name = "total_google_api_books_uploaded_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int totalGoogleApiBooksUploadedForThisAuthor;
	
	@Column(name = "total_books_uploaded_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int totalBooksUploadedForThisAuthor;
	
	@Column(name = "rental_books_uploaded_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int rentalBooksUploadedForThisAuthor;
	
	@Column(name = "swap_books_uploaded_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int swapBooksUploadedForThisAuthor;
	
	@Column(name = "ongoing_interactions_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int ongoingInteractionsForThisAuthor;
	
	@Column(name = "total_fulfilled_interactions_for_this_author", nullable = false, columnDefinition = "INT DEFAULT 0")
	private int totalFulfilledInteractionForThisAuthor;	
	
	@CreationTimestamp
	@Column(name = "book_author_created_at", nullable = false, updatable = false)
	private LocalDateTime bookAuthorCreatedAt;
	
	@UpdateTimestamp
	@Column(name = "book_author_updated_at")
	private LocalDateTime bookAuthorUpdatedAt;
}
