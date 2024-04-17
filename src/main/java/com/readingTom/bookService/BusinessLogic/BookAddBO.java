package com.readingTom.bookService.BusinessLogic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.services.BookAuthorService;
import com.readingTom.bookService.services.BookCategoryService;
import com.readingTom.bookService.services.GoogleApiBookService;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBook;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookImageLinks;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookIndustryIdentifiers;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookRetailPrice;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookSaleInfo;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookVolumeInfo;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempISBNObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BookAddBO {

	@Autowired
//	@Lazy
	private GoogleApiBookService googleApiBookService;
	
	@Autowired
//	@Lazy
	private BookCategoryService bookCategoryService;
	
	@Autowired
//	@Lazy
	private BookAuthorService bookAuthorService;
	
	private final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes/";
	
	
	/*
	 * 
	 */
	//method: to add or update a book in the google api books table
	public GoogleApiBook addingOrUpdatingGoogleApiBook(String incomingGoogleApiBookId, boolean isBookForRent, boolean isBookForSwap, boolean isBookForRentAndSwap) {
		
		log.info("BookAddBO:: addingOrUpdatingGoogleApiBook :: entering to add/update a google api book in system ");
		log.info("*****incomingGoogleApiBookId ::" + incomingGoogleApiBookId );
		log.info("*****isBookForRent ::" + isBookForRent);
		log.info("*****isBookForSwap ::" + isBookForSwap);
		log.info("*****isBookForRentAndSwap ::" + isBookForRentAndSwap);
		
		GoogleApiBook googleApiBook = new GoogleApiBook();
		
		try {
			//first checking if the id exists in the system
			boolean isGoogleApiBookPresent =  this.googleApiBookService.checkGoogleApiBookById(incomingGoogleApiBookId);
			log.info(isGoogleApiBookPresent? "GOOGLEAPIBOOK is PRESENT in the backend" : "GOOGLEAPIBOOK is NOT Present in the backend ");
			
			//if present then we get the book and only update the details
			if(isGoogleApiBookPresent) {
				log.info("fetching the googleApiBook from the backend...");
				
		    	googleApiBook = this.googleApiBookService.getGoogleApiBookById(incomingGoogleApiBookId);
		    		
			}
			//if not present then we fetch the book from api and then add the book
			else {
				log.info("adding the book to the backend...");
				
				String googleApiBookUrl = GOOGLE_API_URL.concat(incomingGoogleApiBookId.trim());
				log.info("hitting the URL to get google api book:: " + googleApiBookUrl);
				
				RestTemplate restTemplate = new RestTemplate();
				
				//calling the google book api and storing the data in temp pojo
				TempGoogleApiBook fetchedBook = restTemplate.getForObject(googleApiBookUrl, TempGoogleApiBook.class);
				TempGoogleApiBookVolumeInfo volumeInfo = fetchedBook.getVolumeInfo();
				TempGoogleApiBookImageLinks imageLinks = volumeInfo.getImageLinks();
				List<TempISBNObject> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
				
				
				//move the below to a function
				
				googleApiBook.setGoogleApiBookId(incomingGoogleApiBookId);
				googleApiBook.setGoogleApiBookTitle(volumeInfo.getTitle());
				googleApiBook.setGoogleApiBookSubTitle(volumeInfo.getSubtitle());
				googleApiBook.setGoogleApiBookPublisher(volumeInfo.getPublisher());
				googleApiBook.setGoogleApiBookPublishedDate(volumeInfo.getPublishedDate());
				googleApiBook.setGoogleApiBookPageCount(volumeInfo.getPageCount());
				googleApiBook.setGoogleApiBookLanguage(volumeInfo.getLanguage());
				//getting isbn10 and isbn13 values
				
				for(TempISBNObject isbnObject : industryIdentifiers) {
					if(isbnObject.getType().equalsIgnoreCase("ISBN_13")) {
						googleApiBook.setGoogleApiBookIsbn13(isbnObject.getIdentifier());
					}else if(isbnObject.getType().equalsIgnoreCase("ISBN_10")) {
						googleApiBook.setGoogleApiBookIsbn10(isbnObject.getIdentifier());
					}
				}
				
				//getting retail price
				TempGoogleApiBookSaleInfo saleInfo = fetchedBook.getSaleInfo();
				TempGoogleApiBookRetailPrice retailPrice = saleInfo.getRetailPrice();
				googleApiBook.setGoogleApiBookRetailPrice(retailPrice.getAmount());
				googleApiBook.setGoogleApiBookRetailPriceCurrencyCode(retailPrice.getCurrencyCode());
				
	//--->	//adding category
				
				List<BookCategory> googleApiBookcategories = googleApiBook.getGoogleApiBookCategories();
				
				List<String> categories = volumeInfo.getCategories();
				for(String categoryName : categories) {
					BookCategory bookCategory = this.bookCategoryService.findByCategoryName(categoryName);
					//if the category exists in db we update it
					if(bookCategory!=null) {
						log.info("category with name : " + categoryName + " exists in the database");
					//if category does not exists add a new category to db
					}else {
						log.info("category with name : " + categoryName + " DOES NOT exists in the database");
						
						bookCategory = new BookCategory();
						
						bookCategory.setCategoryName(categoryName);
						bookCategory.setOngoingInteractionsForThisCategory(0);
						bookCategory.setRentalBooksUploadedForThisCategory(0);
						bookCategory.setSwapBooksUploadedForThisCategory(0);
						bookCategory.setTotalBooksUploadedForThisCategory(0);
						bookCategory.setTotalFulfilledInteractionForThisCategory(0);
						bookCategory.setTotalGoogleApiBooksUploadedForThisCategory(0);
					}
					
					bookCategory.setTotalBooksUploadedForThisCategory(bookCategory.getTotalBooksUploadedForThisCategory() + 1);
					
					if(isBookForRent) {
						bookCategory.setRentalBooksUploadedForThisCategory(bookCategory.getRentalBooksUploadedForThisCategory() + 1);
					}
					if(isBookForSwap) {
						bookCategory.setSwapBooksUploadedForThisCategory(bookCategory.getSwapBooksUploadedForThisCategory() + 1);
					}
					if(isBookForRentAndSwap) {
						bookCategory.setRentalBooksUploadedForThisCategory(bookCategory.getRentalBooksUploadedForThisCategory() + 1);
						bookCategory.setSwapBooksUploadedForThisCategory(bookCategory.getSwapBooksUploadedForThisCategory() + 1);
					}
					
					log.info("^^^^^^^^^^^^^^ bookCategory :: " + bookCategory);
					googleApiBookcategories.add(bookCategory);
					
					//testing if this is working
					this.bookCategoryService.addBookCategory(bookCategory);
				}
				
	//--->	//adding author
				
				List<BookAuthor> googleApiBookAuthors = googleApiBook.getGoogleApiBookAuthors();
				
				List<String> authors = volumeInfo.getAuthors();
				
				
				for(String authorName : authors) {
					BookAuthor bookAuthor = this.bookAuthorService.findByAuthorName(authorName);
					//if the category exists in db we update it
					if(bookAuthor!=null) {
						log.info("author with name : " + authorName + " exists in the database");
					//if category does not exists add a new category to db
					}else {
						log.info("author with name : " + authorName + " DOES NOT exists in the database");
						
						bookAuthor = new BookAuthor();
						
						bookAuthor.setAuthorName(authorName);
						bookAuthor.setOngoingInteractionsForThisAuthor(0);
						bookAuthor.setRentalBooksUploadedForThisAuthor(0);
						bookAuthor.setSwapBooksUploadedForThisAuthor(0);
						bookAuthor.setTotalBooksUploadedForThisAuthor(0);
						bookAuthor.setTotalFulfilledInteractionForThisAuthor(0);
						bookAuthor.setTotalGoogleApiBooksUploadedForThisAuthor(0);
					}
					
					bookAuthor.setTotalBooksUploadedForThisAuthor(bookAuthor.getTotalBooksUploadedForThisAuthor() + 1);
					
					if(isBookForRent) {
						bookAuthor.setRentalBooksUploadedForThisAuthor(bookAuthor.getRentalBooksUploadedForThisAuthor() + 1);
					}
					if(isBookForSwap) {
						bookAuthor.setSwapBooksUploadedForThisAuthor(bookAuthor.getSwapBooksUploadedForThisAuthor() + 1);
					}
					if(isBookForRentAndSwap) {
						bookAuthor.setRentalBooksUploadedForThisAuthor(bookAuthor.getRentalBooksUploadedForThisAuthor() + 1);
						bookAuthor.setSwapBooksUploadedForThisAuthor(bookAuthor.getSwapBooksUploadedForThisAuthor() + 1);
					}
					
					log.info("^^^^^^^^^^^^^^ bookAuthor :: " + bookAuthor);

					googleApiBookAuthors.add(bookAuthor);
					
					//testing if this is working
					this.bookAuthorService.addBookAuthor(bookAuthor);
				}
				
				
				
				
				
				
				//local system columns : updates are done below
				googleApiBook.setTotalBooksUploadedForThisGoogleApiBook(0);
				googleApiBook.setRentalBooksUploadedForThisGoogleApiBook(0);
				googleApiBook.setSwapBooksUploadedForThisGoogleApiBook(0);
				googleApiBook.setOngoingInteractionsForThisGoogleApiBook(0);
				googleApiBook.setTotalFulfilledInteractionForThisGoogleApiBook(0);
			}
			
			
			googleApiBook.setTotalBooksUploadedForThisGoogleApiBook(googleApiBook.getTotalBooksUploadedForThisGoogleApiBook() + 1);

			if(isBookForRent) {
	    		googleApiBook.setRentalBooksUploadedForThisGoogleApiBook(googleApiBook.getRentalBooksUploadedForThisGoogleApiBook() + 1);
	    	}
			if(isBookForSwap) {
				googleApiBook.setSwapBooksUploadedForThisGoogleApiBook(googleApiBook.getSwapBooksUploadedForThisGoogleApiBook() + 1);
			}
			if(isBookForRentAndSwap) {
				googleApiBook.setRentalBooksUploadedForThisGoogleApiBook(googleApiBook.getRentalBooksUploadedForThisGoogleApiBook() + 1);
				googleApiBook.setSwapBooksUploadedForThisGoogleApiBook(googleApiBook.getSwapBooksUploadedForThisGoogleApiBook() + 1);
			}
			
			
		}catch(Exception e) {
			log.error("BookAddBO :: addingOrUpdatingGoogleApiBook :: Error while adding the book");
			e.printStackTrace();
		}
		
		log.info("successfully received/updated the Google api book...");
		log.info("^^^^^^^^^^^ googleApiBook :: " + googleApiBook);
		
		return googleApiBook;
	}
	
	
	/*
	 * 
	 */
	
	public BookAuthor addingGoogleApiBookAuthor() {
	
		return null;
	}
}
