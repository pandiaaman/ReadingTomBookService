package com.readingTom.bookService.BusinessLogic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.helper.RestTemplateProviderSingleton;
import com.readingTom.bookService.services.BookAuthorService;
import com.readingTom.bookService.services.BookCategoryService;
import com.readingTom.bookService.services.GoogleApiBookService;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBook;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookImageLinks;
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
	
	@Autowired
	private RestTemplateProviderSingleton restTemplateProvider;
	
	private final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes/";
	
	
	/*
	 * 
	 */
	//method: to add or update a book in the google api books table
	public GoogleApiBook addingOrUpdatingGoogleApiBook(String incomingGoogleApiBookId, boolean isBookForRent, boolean isBookForSwap) {
		
		log.info("BookAddBO:: addingOrUpdatingGoogleApiBook :: entering to add/update a google api book in system ");
		log.info("*****incomingGoogleApiBookId ::" + incomingGoogleApiBookId );
		log.info("*****isBookForRent ::" + isBookForRent);
		log.info("*****isBookForSwap ::" + isBookForSwap);
		
		
		GoogleApiBook googleApiBook = new GoogleApiBook();
		
		try {
			//first checking if the id exists in the system
			boolean isGoogleApiBookPresent =  this.googleApiBookService.checkGoogleApiBookById(incomingGoogleApiBookId);
			log.info(isGoogleApiBookPresent? "GOOGLEAPIBOOK is PRESENT in the backend" : "GOOGLEAPIBOOK is NOT Present in the backend ");
			
			//if present then we get the book and only update the details
			if(isGoogleApiBookPresent) {
				log.info("fetching the googleApiBook from the backend...");
				
		    	googleApiBook = getGoogleApiBookFromBackend(incomingGoogleApiBookId,googleApiBook);
		    	
		    	//if we are getting an existing book this means that we already have the author and category info added
		    	//we only need to update the book uploaded detail that we are doing later on after else part
		    	//BUT we also need to update the author and category sections for this book
		    		
			}
			//if not present then we fetch the book from api and then add the book
			else {
				log.info("adding the book to the backend...");
				
				googleApiBook = createGoogleApiBookToAdd(incomingGoogleApiBookId, googleApiBook, isBookForRent, isBookForSwap);
				
				
				
			}
			
			
			List<BookCategory> updatedBookCategories = updatedBookCategories(googleApiBook, isBookForRent, isBookForSwap);
			googleApiBook.setGoogleApiBookCategories(updatedBookCategories);
			
			
			List<BookAuthor> updatedBookAuthors = updatedBookAuthors(googleApiBook, isBookForRent, isBookForSwap);
			googleApiBook.setGoogleApiBookAuthors(updatedBookAuthors);
			
			
			
			
			log.info("Updating the Google API book info related to upload of a new book by user...");
			
			googleApiBook.setTotalBooksUploadedForThisGoogleApiBook(googleApiBook.getTotalBooksUploadedForThisGoogleApiBook() + 1);

			if(isBookForRent) {
	    		googleApiBook.setRentalBooksUploadedForThisGoogleApiBook(googleApiBook.getRentalBooksUploadedForThisGoogleApiBook() + 1);
	    	}
			if(isBookForSwap) {
				googleApiBook.setSwapBooksUploadedForThisGoogleApiBook(googleApiBook.getSwapBooksUploadedForThisGoogleApiBook() + 1);
			}
			
			
			
		}catch(Exception e) {
			log.error("BookAddBO :: addingOrUpdatingGoogleApiBook :: Error while adding or updating the book");
			e.printStackTrace();
		}
		
		log.info("successfully received/updated the Google api book...");
		
		return googleApiBook;
	}
	
	/*
	 * 
	 */
	 List<BookCategory> updatedBookCategories(GoogleApiBook googleApiBook, boolean isBookForRent, boolean isBookForSwap) {
		List<BookCategory> updatedBookCategories = new ArrayList<>();
		List<BookCategory> categories = googleApiBook.getGoogleApiBookCategories();
		for(BookCategory bookCategory : categories) {
			bookCategory.setTotalBooksUploadedForThisCategory(bookCategory.getTotalBooksUploadedForThisCategory() + 1);
			
			if(isBookForRent) {
				bookCategory.setRentalBooksUploadedForThisCategory(bookCategory.getRentalBooksUploadedForThisCategory() + 1);
			}
			if(isBookForSwap) {
				bookCategory.setSwapBooksUploadedForThisCategory(bookCategory.getSwapBooksUploadedForThisCategory() + 1);
			}
			
			bookCategory = this.bookCategoryService.addBookCategory(bookCategory);
			
			updatedBookCategories.add(bookCategory);
			
		}
		
		
		return updatedBookCategories;
	}
	 
	 
	/*
	 * 
	 */
	 List<BookAuthor> updatedBookAuthors(GoogleApiBook googleApiBook, boolean isBookForRent, boolean isBookForSwap) {
		List<BookAuthor> updatedBookAuthors = new ArrayList<>();
		List<BookAuthor> authors = googleApiBook.getGoogleApiBookAuthors();
		for(BookAuthor bookAuthor : authors) {
			bookAuthor.setTotalBooksUploadedForThisAuthor(bookAuthor.getTotalBooksUploadedForThisAuthor() + 1);
			
			if(isBookForRent) {
				bookAuthor.setRentalBooksUploadedForThisAuthor(bookAuthor.getRentalBooksUploadedForThisAuthor() + 1);
			}
			if(isBookForSwap) {
				bookAuthor.setSwapBooksUploadedForThisAuthor(bookAuthor.getSwapBooksUploadedForThisAuthor() + 1);
			}
			bookAuthor = this.bookAuthorService.addBookAuthor(bookAuthor);
		
			updatedBookAuthors.add(bookAuthor);
			
		}
		
		return updatedBookAuthors;
	}	 
	 


	/*
	 * 
	 */
	
	private GoogleApiBook getGoogleApiBookFromBackend(String incomingGoogleApiBookId, GoogleApiBook googleApiBook) {
	
		log.info("inside getGoogleApiBookFromBackend...");
		
		googleApiBook = this.googleApiBookService.getGoogleApiBookById(incomingGoogleApiBookId);
		return googleApiBook;
	}
	
	/*
	 * 
	 */
	private GoogleApiBook createGoogleApiBookToAdd(String incomingGoogleApiBookId, GoogleApiBook googleApiBook, boolean isBookForRent, boolean isBookForSwap) {
		log.info("addGoogleApiBookToBackend :: adding the google book to the backend...");
		
		String googleApiBookUrl = GOOGLE_API_URL.concat(incomingGoogleApiBookId.trim());
		log.info("hitting the URL to get google api book:: " + googleApiBookUrl);
		
		//calling singleton class for restTemplate
		RestTemplate restTemplate = restTemplateProvider.getRestTemplate();
		
		//calling the google book api and storing the data in temp pojo
		TempGoogleApiBook fetchedBook = restTemplate.getForObject(googleApiBookUrl, TempGoogleApiBook.class);
		TempGoogleApiBookVolumeInfo volumeInfo = fetchedBook.getVolumeInfo();
		TempGoogleApiBookImageLinks imageLinks = volumeInfo.getImageLinks();
		TempGoogleApiBookSaleInfo saleInfo = fetchedBook.getSaleInfo();
		List<TempISBNObject> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
		
		
		//move the below to a function
		
		googleApiBook.setGoogleApiBookId(incomingGoogleApiBookId);
		if(volumeInfo!=null) {
			googleApiBook.setGoogleApiBookTitle(volumeInfo.getTitle()!=null? volumeInfo.getTitle() : "");
			googleApiBook.setGoogleApiBookSubTitle(volumeInfo.getSubtitle()!=null? volumeInfo.getSubtitle() : "");
			googleApiBook.setGoogleApiBookPublisher(volumeInfo.getPublisher()!=null? volumeInfo.getPublisher() : "");
			googleApiBook.setGoogleApiBookPublishedDate(volumeInfo.getPublishedDate()!=null? volumeInfo.getPublishedDate() : "");
			googleApiBook.setGoogleApiBookPageCount(volumeInfo.getPageCount());
			googleApiBook.setGoogleApiBookLanguage(volumeInfo.getLanguage()!=null? volumeInfo.getLanguage() : "");
		}
		
		if(imageLinks!=null) {
			//base url to be added before using any URL: https://books.google.com/books/content?id=
			googleApiBook.setGoogleApiBookThumbnailUrl(imageLinks.getThumbnail()!=null? shortenUrl(imageLinks.getThumbnail()) : "");
			googleApiBook.setGoogleApiBookSmallThumbnailUrl(imageLinks.getSmallThumbnail()!=null? shortenUrl(imageLinks.getSmallThumbnail()) : "")	;
		}
		
		
		if(industryIdentifiers!=null) {
			//getting isbn10 and isbn13 values
			for(TempISBNObject isbnObject : industryIdentifiers) {
				if(isbnObject.getType().equalsIgnoreCase("ISBN_13")) {
					googleApiBook.setGoogleApiBookIsbn13(isbnObject.getIdentifier());
				}else if(isbnObject.getType().equalsIgnoreCase("ISBN_10")) {
					googleApiBook.setGoogleApiBookIsbn10(isbnObject.getIdentifier());
				}
			}
		}
		

		if(saleInfo!=null) {
			//getting retail price
			TempGoogleApiBookRetailPrice retailPrice = saleInfo.getRetailPrice();
			if(retailPrice!=null) {
				googleApiBook.setGoogleApiBookRetailPrice(retailPrice.getAmount());
				googleApiBook.setGoogleApiBookRetailPriceCurrencyCode(retailPrice.getCurrencyCode()!=null? retailPrice.getCurrencyCode() : "");
			}
			
		}
		
		
		//we get the list of categories and authors and add it here to the googleApiBook
//--->	//adding category
		
		List<BookCategory> googleApiBookCategories = getGoogleApiBookCategories(googleApiBook, volumeInfo, isBookForRent, isBookForSwap);
		googleApiBook.setGoogleApiBookCategories(googleApiBookCategories);
		
//--->	//adding author
		
		List<BookAuthor> googleApiBookAuthors = getGoogleApiBookAuthors(googleApiBook, volumeInfo, isBookForRent, isBookForSwap);
		googleApiBook.setGoogleApiBookAuthors(googleApiBookAuthors);
		
		return googleApiBook;
	}
	
	/*
	 * 
	 */
	private static String shortenUrl(String longUrl) {
		
		log.info("shortening the url..." + longUrl);
		
		int urlContentLatterPartIndex = -1;
		String shortUrl="";
		
		urlContentLatterPartIndex = longUrl.indexOf('=');
		
		if(urlContentLatterPartIndex == -1) {
			log.error("No = found in the URL");
			return "XxcrAQAAIAAJ&printsec=frontcover&img=1&zoom=5&source=gbs_api";
		}else {
			shortUrl = longUrl.substring(urlContentLatterPartIndex + 1); //starts after =
		}
		
		log.info("shortened part of the URL: " + shortUrl);		
		
		return shortUrl;
	}
	
	/*
	 * 
	 */
	private List<BookAuthor> getGoogleApiBookAuthors(GoogleApiBook googleApiBook, TempGoogleApiBookVolumeInfo volumeInfo, boolean isBookForRent, boolean isBookForSwap) {
	
		List<BookAuthor> googleApiBookAuthors = new ArrayList<>();
		
		List<String> authors = new ArrayList<>();
		
		if(volumeInfo!=null) {
			authors = volumeInfo.getAuthors();
		}
		
		log.info("number of authors associated with this book are : " + authors.size());
		
		if(authors.size() > 0) {
		
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
				}
				
//				
				
				log.info("^^^^^^^^^^^^^^ bookAuthor :: " + bookAuthor);

				
				
				//testing if this is working
				bookAuthor = this.bookAuthorService.addBookAuthor(bookAuthor);
				
				googleApiBookAuthors.add(bookAuthor);
			}
		}
		
		
		return googleApiBookAuthors;
	}
	
	/*
	 * 
	 */
	private List<BookCategory> getGoogleApiBookCategories(GoogleApiBook googleApiBook, TempGoogleApiBookVolumeInfo volumeInfo, boolean isBookForRent, boolean isBookForSwap) {
		log.info("reaching getGoogleApiBookCategories to fetch the categories of the book...");
		
//		List<BookCategory> googleApiBookcategories = googleApiBook.getGoogleApiBookCategories();
		List<BookCategory> googleApiBookcategories = new ArrayList<>();;
		
		List<String> categories = new ArrayList<>();
		
		if(volumeInfo!=null) {
			categories = volumeInfo.getCategories();
		}
		
		log.info("number of categories associated with this book are : " + categories.size());
		
		if(categories.size() > 0) {
			
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
				}
				
				
				log.info("^^^^^^^^^^^^^^ bookCategory :: " + bookCategory);
				bookCategory = this.bookCategoryService.addBookCategory(bookCategory);
				
				googleApiBookcategories.add(bookCategory);
				
			}
		}
		
		return googleApiBookcategories;
	}
}
