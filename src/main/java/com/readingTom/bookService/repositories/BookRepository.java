package com.readingTom.bookService.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.readingTom.bookService.entities.Book;

public interface BookRepository extends PagingAndSortingRepository<Book, String>, JpaRepository<Book, String>{

	Optional<Book> findById(String id);
	
	//
	Page<Book> findByBookOwnerId(String ownerId, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndIsBookAvailableAndIsBookForRent(String ownerId, boolean isBookAvailable, boolean isBookForRent, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndIsBookAvailableAndIsBookForSwap(String ownerId, boolean isBookAvailable, boolean isBookForSwap, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String ownerId, boolean isBookAvailable, boolean isBookForRent, boolean isBookVerified, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String ownerId, boolean isBookAvailable, boolean isBookForSwap, boolean isBookVerified, Pageable pageable);
	
	//
	Page<Book> findByGoogleApiBookId(String googleApiBookId, Pageable pageable);
	
	Page<Book> findByGoogleApiBookIdAndIsBookAvailableAndIsBookForRent(String googleApiBookId, boolean isBookAvailable, boolean isBookForRent, Pageable pageable);
	
	Page<Book> findByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwap(String googleApiBookId, boolean isBookAvailable, boolean isBookForSwap, Pageable pageable);

	Page<Book> findByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String googleApiBookId, boolean isBookAvailable, boolean isBookForRent, boolean isBookVerified, Pageable pageable);

	Page<Book> findByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String googleApiBookId, boolean isBookAvailable, boolean isBookForSwap, boolean isBookVerified, Pageable pageable);
	
	Page<Book> findByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerifiedAndBookRentalPricePerDayBetween(String googleApiBookId, boolean isBookAvailable, boolean isBookForSwap, boolean isBookVerified, int minPrice, int maxPrice, Pageable pageable);
	
	//
	Page<Book> findByBookOwnerIdAndGoogleApiBookId(String ownerId, String googleApiBookId, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForRent(String ownerId, String googleApiBookId, boolean isBookAvailable, boolean isBookForRent, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForSwap(String ownerId, String googleApiBookId, boolean isBookAvailable, boolean isBookForSwap, Pageable pageable);
	
	Page<Book> findByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(String ownerId, String googleApiBookId, boolean isBookAvailable, boolean isBookForRent, boolean isBookVerified, Pageable pageable);

	Page<Book> findByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(String ownerId, String googleApiBookId, boolean isBookAvailable, boolean isBookForSwap, boolean isBookVerified, Pageable pageable);




}
