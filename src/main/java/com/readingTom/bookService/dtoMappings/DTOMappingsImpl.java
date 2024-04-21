package com.readingTom.bookService.dtoMappings;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.readingTom.bookService.dto.BookAuthorResponseDTO;
import com.readingTom.bookService.dto.BookCategoryResponseDTO;
import com.readingTom.bookService.dto.BookRequestDTO;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.dto.GoogleApiBookResponseDTO;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Service
public class DTOMappingsImpl implements DTOMappings{

	@Override
	public Book mapBookRequestDtoToBook(BookRequestDTO bookRequestDto) {
		log.info("DTOMappingsImpl :: mapBookRequestDtoToBook :: preparing Book from incoming request...");
		Book book = new Book();
		
		book.setBookOwnerId(bookRequestDto.getBookOwnerId());
		book.setGoogleApiBookId(bookRequestDto.getGoogleApiBookId());
		book.setBookForRent(bookRequestDto.isBookForRent());
		book.setBookForSwap(bookRequestDto.isBookForSwap());
		book.setUserUploadedBookImage1TemporaryUrl(bookRequestDto.getUserUploadedBookImage1TemporaryUrl());
		book.setUserUploadedBookImage2TemporaryUrl(bookRequestDto.getUserUploadedBookImage2TemporaryUrl());
		book.setUserUploadedBookBarcodeImageTemporaryUrl(bookRequestDto.getUserUploadedBookBarcodeImageTemporaryUrl());
		book.setUserUploadedBookDetails(bookRequestDto.getUserUploadedBookDetails());
		
		return book;
	}

	@Override
	public BookResponseDTO mapBookToBookResponseDto(Book book) {
		
		log.info("DTOMappingsImpl :: mapBookToBookResponseDto :: preparing response for book object...");
		BookResponseDTO bookResponse = new BookResponseDTO();
		
//		private String bookId;
		bookResponse.setBookId(book.getBookId());
		
//		private String googleApiBookId;
		bookResponse.setGoogleApiBookId(book.getGoogleApiBookId());
//		
		//TODO: create a new mapping to show the google api book here
		bookResponse.setGoogleApiBook(mapGoogleApiBookToGoogleApiBookResponseDTO(book.getGoogleApiBook()));
//		
//		private String bookOwnerId;
		bookResponse.setBookOwnerId(book.getBookOwnerId());
//		
//		private String bookOwner; 
//		
		//TODO: set the bookowner once we have the owner class in here
		
//		private boolean isBookAvailable;
		bookResponse.setBookAvailable(book.isBookAvailable());
//		
//		private boolean isBookSwapped;
		bookResponse.setBookSwapped(book.isBookSwapped());
//		
//		private boolean isBookRentedAtThisTime;
		bookResponse.setBookRentedAtThisTime(book.isBookRentedAtThisTime());
//		
//		private boolean isBookForRent;
		bookResponse.setBookForRent(book.isBookForRent());
//		
//		private boolean isBookForSwap;
		bookResponse.setBookForSwap(book.isBookForSwap());
//		
//		private int totalOngoingInteractionsForThisBook;
		bookResponse.setTotalOngoingInteractionsForThisBook(book.getTotalOngoingInteractionsForThisBook());
//		
//		private LocalDateTime bookCreatedAt;
		bookResponse.setBookCreatedAt(book.getBookCreatedAt());
//		
//		private LocalDateTime bookUpdatedAt;
		bookResponse.setBookUpdatedAt(book.getBookUpdatedAt());
//		
//		private String userUploadedBookImage1TemporaryUrl;
		bookResponse.setUserUploadedBookImage1TemporaryUrl(book.getUserUploadedBookImage1TemporaryUrl());
//		
//		private String userUploadedBookImage2TemporaryUrl;
		bookResponse.setUserUploadedBookImage2TemporaryUrl(book.getUserUploadedBookImage2TemporaryUrl());
//		
//		private String userUploadedBookBarcodeImageTemporaryUrl;
		bookResponse.setUserUploadedBookBarcodeImageTemporaryUrl(book.getUserUploadedBookBarcodeImageTemporaryUrl());
//
//		private String userUploadedBookImage1PermanentUrl;
		bookResponse.setUserUploadedBookImage1PermanentUrl(book.getUserUploadedBookImage1PermanentUrl());
//		
//		private String userUploadedBookImage2PermanentUrl;
		bookResponse.setUserUploadedBookImage2PermanentUrl(book.getUserUploadedBookImage2PermanentUrl());
//		
//		private String userUploadedBookBarcodeImagePermanentUrl;
		bookResponse.setUserUploadedBookBarcodeImagePermanentUrl(book.getUserUploadedBookBarcodeImagePermanentUrl());
//		
//		private String userUploadedBookBarcodeIsbnValue;
		bookResponse.setUserUploadedBookBarcodeIsbnValue(book.getUserUploadedBookBarcodeIsbnValue());
//		
//		private String userUploadedBookDetails;
		bookResponse.setUserUploadedBookDetails(book.getUserUploadedBookDetails());
//		
//		private boolean isBookVerified;
		bookResponse.setBookVerified(book.isBookVerified());
		
		
		
		return bookResponse;
	}

	@Override
	public GoogleApiBookResponseDTO mapGoogleApiBookToGoogleApiBookResponseDTO(GoogleApiBook googleApiBook) {
		
		log.info("DTOMappingsImpl :: mapGoogleApiBooktoGoogleApiBookResponseDTO :: preparing response for google api book object...");
		GoogleApiBookResponseDTO googleApiBookResponse = new GoogleApiBookResponseDTO();
		
		googleApiBookResponse.setGoogleApiBookId(googleApiBook.getGoogleApiBookId());
		googleApiBookResponse.setGoogleApiBookTitle(googleApiBook.getGoogleApiBookTitle());
		googleApiBookResponse.setGoogleApiBookSubTitle(googleApiBook.getGoogleApiBookSubTitle());
		googleApiBookResponse.setBooksIdUploadedForThisGoogleApiBook(fetchIdOfAllBooksUploadedForThisGoogleApiBook(googleApiBook.getUploadedBooksListForThisGoogleApiBook()));
		//prepare list of categories and author details that we need to show
		googleApiBookResponse.setGoogleApiBookAuthors(prepareBookAuthorListForGoogleApiBook(googleApiBook.getGoogleApiBookAuthors()));
		googleApiBookResponse.setGoogleApiBookCategories(prepareBookCategoryListForGoogleApiBook(googleApiBook.getGoogleApiBookCategories()));
		googleApiBookResponse.setGoogleApiBookThumbnailUrl(googleApiBook.getGoogleApiBookThumbnailUrl());
		googleApiBookResponse.setGoogleApiBookSmallThumbnailUrl(googleApiBook.getGoogleApiBookSmallThumbnailUrl());
		googleApiBookResponse.setGoogleApiBookPublisher(googleApiBook.getGoogleApiBookPublisher());
		googleApiBookResponse.setGoogleApiBookPublishedDate(googleApiBook.getGoogleApiBookPublishedDate());
		googleApiBookResponse.setGoogleApiBookPageCount(googleApiBook.getGoogleApiBookPageCount());
		googleApiBookResponse.setGoogleApiBookLanguage(googleApiBook.getGoogleApiBookLanguage());
		googleApiBookResponse.setGoogleApiBookIsbn10(googleApiBook.getGoogleApiBookIsbn10());
		googleApiBookResponse.setGoogleApiBookIsbn13(googleApiBook.getGoogleApiBookIsbn13());
		googleApiBookResponse.setGoogleApiBookRetailPrice(googleApiBook.getGoogleApiBookRetailPrice());
		googleApiBookResponse.setGoogleApiBookRetailPriceCurrencyCode(googleApiBook.getGoogleApiBookRetailPriceCurrencyCode());
		googleApiBookResponse.setTotalBooksUploadedForThisGoogleApiBook(googleApiBook.getTotalBooksUploadedForThisGoogleApiBook());
		googleApiBookResponse.setRentalBooksUploadedForThisGoogleApiBook(googleApiBook.getRentalBooksUploadedForThisGoogleApiBook());
		googleApiBookResponse.setSwapBooksUploadedForThisGoogleApiBook(googleApiBook.getSwapBooksUploadedForThisGoogleApiBook());
		googleApiBookResponse.setOngoingInteractionsForThisGoogleApiBook(googleApiBook.getOngoingInteractionsForThisGoogleApiBook());
		googleApiBookResponse.setTotalFulfilledInteractionForThisGoogleApiBook(googleApiBook.getTotalFulfilledInteractionForThisGoogleApiBook());
		googleApiBookResponse.setGoogleApiBookCreatedAt(googleApiBook.getGoogleApiBookCreatedAt());
		googleApiBookResponse.setGoogleApiBookUpdatedAt(googleApiBook.getGoogleApiBookUpdatedAt());
		
		return googleApiBookResponse;
	}

	
	public List<String> fetchIdOfAllBooksUploadedForThisGoogleApiBook(List<Book> booksUploadedForThisGoogleApiBook){
		
		List<String> booksIdList = new ArrayList<>();
		
		if(booksUploadedForThisGoogleApiBook.size() > 0) {
			for(Book book : booksUploadedForThisGoogleApiBook) {
				booksIdList.add(book.getBookId());
			}
		}
		
		return booksIdList;
	}
	
	public List<BookAuthorResponseDTO> prepareBookAuthorListForGoogleApiBook(List<BookAuthor> authors){
		
		List<BookAuthorResponseDTO> authorsList = new ArrayList<>();
		
		if(authors.size() > 0) {
			for(BookAuthor author : authors) {
				BookAuthorResponseDTO authorResponse = mapBookAuthorToBookAuthorResponseDTO(author);
				authorsList.add(authorResponse);
			}
			
		}		
		return authorsList;
	}
	
	public List<BookCategoryResponseDTO> prepareBookCategoryListForGoogleApiBook(List<BookCategory> categories){
		
		List<BookCategoryResponseDTO> categoriesList = new ArrayList<>();
		
		if(categories.size() > 0) {
			for(BookCategory category : categories) {
				BookCategoryResponseDTO categoryResponse = mapBookCategoryTOBookCategoryResponseDTO(category);
				categoriesList.add(categoryResponse);
			}
		}
		
		return categoriesList;
	}
	
	@Override
	public BookAuthorResponseDTO mapBookAuthorToBookAuthorResponseDTO(BookAuthor bookAuthor) {
		
		log.info("DTOMappingsImpl :: mapBookAuthorToBookAuthorResponseDTO :: preparing response for book author object...");
		BookAuthorResponseDTO bookAuthorResponse = new BookAuthorResponseDTO();
		
		bookAuthorResponse.setAuthorName(bookAuthor.getAuthorName());
		bookAuthorResponse.setTotalGoogleApiBooksUploadedForThisAuthor(bookAuthor.getTotalGoogleApiBooksUploadedForThisAuthor());
		bookAuthorResponse.setTotalBooksUploadedForThisAuthor(bookAuthor.getTotalBooksUploadedForThisAuthor());
		bookAuthorResponse.setRentalBooksUploadedForThisAuthor(bookAuthor.getRentalBooksUploadedForThisAuthor());
		bookAuthorResponse.setSwapBooksUploadedForThisAuthor(bookAuthor.getSwapBooksUploadedForThisAuthor());
		bookAuthorResponse.setOngoingInteractionsForThisAuthor(bookAuthor.getOngoingInteractionsForThisAuthor());
		bookAuthorResponse.setTotalFulfilledInteractionForThisAuthor(bookAuthor.getTotalFulfilledInteractionForThisAuthor());
		bookAuthorResponse.setBookAuthorCreatedAt(bookAuthor.getBookAuthorCreatedAt());
		bookAuthorResponse.setBookAuthorUpdatedAt(bookAuthor.getBookAuthorUpdatedAt());
		
		return bookAuthorResponse;
	}

	@Override
	public BookCategoryResponseDTO mapBookCategoryTOBookCategoryResponseDTO(BookCategory bookCategory) {
		
		log.info("DTOMappingsImpl :: mapGoogleApiBooktoGoogleApiBookResponseDTO :: preparing response for book category object...");
		BookCategoryResponseDTO bookCategoryResponse = new BookCategoryResponseDTO();
		
		bookCategoryResponse.setCategoryName(bookCategory.getCategoryName());
		bookCategoryResponse.setTotalGoogleApiBooksUploadedForThisCategory(bookCategory.getTotalGoogleApiBooksUploadedForThisCategory());
		bookCategoryResponse.setTotalBooksUploadedForThisCategory(bookCategory.getTotalBooksUploadedForThisCategory());
		bookCategoryResponse.setRentalBooksUploadedForThisCategory(bookCategory.getRentalBooksUploadedForThisCategory());
		bookCategoryResponse.setSwapBooksUploadedForThisCategory(bookCategory.getSwapBooksUploadedForThisCategory());
		bookCategoryResponse.setOngoingInteractionsForThisCategory(bookCategory.getOngoingInteractionsForThisCategory());
		bookCategoryResponse.setTotalFulfilledInteractionForThisCategory(bookCategory.getTotalFulfilledInteractionForThisCategory());
		bookCategoryResponse.setBookCategoryCreatedAt(bookCategory.getBookCategoryCreatedAt());
		bookCategoryResponse.setBookCategoryUpdatedAt(bookCategory.getBookCategoryUpdatedAt());
		
		return bookCategoryResponse;
	}

	//converting 
}
