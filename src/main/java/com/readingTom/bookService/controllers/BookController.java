package com.readingTom.bookService.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.readingTom.bookService.BusinessLogic.BookAddBO;
import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.dto.BookRequestDTO;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.dtoMappings.DTOMappings;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.repositories.GoogleApiBookRepository;
import com.readingTom.bookService.services.BookService;
import com.readingTom.bookService.services.GoogleApiBookService;
import com.readingTom.bookService.services.impl.GoogleApiBookServiceImpl;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBook;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookImageLinks;
import com.readingTom.bookService.tempGoogleApiBookMappers.TempGoogleApiBookVolumeInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private DTOMappings dtoMappings;

	public BookController() {
		super();
		log.info("inside book controller...");
	}
	
	
	//http://localhost:8081/bookservice/book/all?page=0&size=8&ascending=false&sortby=bookId
	//getting books
	@GetMapping(value = "/all", produces= {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllBooks(
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "bookCreatedAt") String sortby){
		try {
			log.info("BookController: getting all books..." + page + " " + size + " " + ascending + " " + sortby);
			
			List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(ascending?Sort.Direction.ASC:Sort.Direction.DESC, sortby));
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
			
			List<Book> allBooks = this.bookService.getAllBooks(pageable);
			
			//if books fetched array is empty
			if(allBooks.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			
			//converting output to DTOS\
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			for(Book book : allBooks) {
				BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
				
				outgoingBooks.add(bookResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/*
	 * 
	 */
	@GetMapping(value = "/forrentbyowner/{ownerId}", produces = {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllVerifiedAvailableBooksByOwnerIdForRent(
			@PathVariable String ownerId,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "bookCreatedAt") String sortby
			){
		
		try {
			log.info("BookController :: getAllVerifiedAvailableBooksByOwnerIdForRent");
			
			List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(ascending?Sort.Direction.ASC:Sort.Direction.DESC, sortby));
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
			
			List<Book> allVerifiedAvailableBooksByOwnerIdForRent = this.bookService.getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(ownerId, true, pageable);
			
			
			//if books fetched array is empty
			if(allVerifiedAvailableBooksByOwnerIdForRent.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			
			//converting output to DTOS\
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			for(Book book : allVerifiedAvailableBooksByOwnerIdForRent) {
				BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
				
				outgoingBooks.add(bookResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	/*
	 * 
	 */
	@GetMapping(value = "/forswapbyowner/{ownerId}", produces = {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllVerifiedAvailableBooksByOwnerIdForSwap(
			@PathVariable String ownerId,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "bookCreatedAt") String sortby
			){
		
		try {
			log.info("BookController :: getAllVerifiedAvailableBooksByOwnerIdForSwap");
			
			List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(ascending?Sort.Direction.ASC:Sort.Direction.DESC, sortby));
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
			
			List<Book> allVerifiedAvailableBooksByOwnerIdForSwap = this.bookService.getBooksByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(ownerId, true, pageable);
			
			
			//if books fetched array is empty
			if(allVerifiedAvailableBooksByOwnerIdForSwap.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			
			//converting output to DTOS\
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			for(Book book : allVerifiedAvailableBooksByOwnerIdForSwap) {
				BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
				
				outgoingBooks.add(bookResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	@GetMapping(value = "/forrentbygooglebook/{googleApiBookId}", produces = {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllVerifiedAvailableBooksByGoogleApiIdForRent(
			@PathVariable String googleApiBookId,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "bookCreatedAt") String sortby
			){
		try {
			log.info("BookController :: getAllVerifiedAvailableBooksByGoogleApiIdForRent");
			
			List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(ascending?Sort.Direction.ASC:Sort.Direction.DESC, sortby));
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
			
			List<Book> allVerifiedAvailableBooksByGoogleApiBookIdForRent = this.bookService.getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified(googleApiBookId, true, pageable);
			
			
			//if books fetched array is empty
			if(allVerifiedAvailableBooksByGoogleApiBookIdForRent.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			
			//converting output to DTOS\
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			for(Book book : allVerifiedAvailableBooksByGoogleApiBookIdForRent) {
				BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
				
				outgoingBooks.add(bookResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	@GetMapping(value = "/forswapbygooglebook/{googleApiBookId}", produces = {"application/json","application/xml"})
	public ResponseEntity<List<BookResponseDTO>> getAllVerifiedAvailableBooksByGoogleApiIdForSwap(
			@PathVariable String googleApiBookId,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "2") int size,
			@RequestParam(defaultValue = "true") boolean ascending,
			@RequestParam(defaultValue = "bookCreatedAt") String sortby
			){
		
		try {
			log.info("BookController :: getAllVerifiedAvailableBooksByGoogleApiIdForSwap");
			
			List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(ascending?Sort.Direction.ASC:Sort.Direction.DESC, sortby));
			
			Pageable pageable = PageRequest.of(page, size, Sort.by(sortOrder));
			
			List<Book> allVerifiedAvailableBooksByGoogleApiBookIdForSwap = this.bookService.getBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified(googleApiBookId, true, pageable);
			
			
			//if books fetched array is empty
			if(allVerifiedAvailableBooksByGoogleApiBookIdForSwap.size() == 0) {
				throw new BookNotFoundException("No books available in the system");
			}
			
			//converting output to DTOS\
			List<BookResponseDTO> outgoingBooks = new ArrayList<>();
			
			for(Book book : allVerifiedAvailableBooksByGoogleApiBookIdForSwap) {
				BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
				
				outgoingBooks.add(bookResponse);
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
		}catch(Exception e) {
			log.error("error in getting all the books");
			e.printStackTrace();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		
	}
	
	
	
	
	//get one book
	@GetMapping(value = "/{bookId}", produces= {"application/json","application/xml"})
	public ResponseEntity<BookResponseDTO> getOneBook(@PathVariable String bookId) {
		try {
			log.info("BookController: getting book with id " + bookId);
			Book book = this.bookService.getBookById(bookId);
			
			BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(book);
			
			return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
		}catch(Exception e) {
			log.error("error in getting the book");
			e.printStackTrace(); //TODO we can throw the book not found exception here (custom exception)
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
	
	
	
	
	@Autowired
	private GoogleApiBookService googleApiBookService;
	
	@Autowired
	BookAddBO bookAdditionBusinessObject;
	
	//add book
	@PostMapping(value = "/add",
			produces= {"application/json","application/xml"},
			consumes= {"application/json","application/xml"})
	public ResponseEntity<BookResponseDTO> addBook(@RequestBody BookRequestDTO incomingBook) {
		try {
			log.info("BookController: adding the book...");
			
			//BusinessLogic : TODO: later move these to different methods
			/*
			 * on add book request we take googleapibookid and call google api and fetch the data
			 * this request can be made asynchronously for which we can use web client
			 * using the data we first create an object of googleapibook
			 * inside googleapibook we have categories and authors
			 * while creating that object, we take values from the list and create object for categories and authors
			 * finally when all are added we save the googleapibook and then we save the book
			 */
			log.info("ISBOOKFORRENTAMAN: " + incomingBook.isBookForRent() + " " + incomingBook.isBookForSwap() + " "+ incomingBook.getGoogleApiBookId());
			//converting to DTOs
			Book book = dtoMappings.mapBookRequestDtoToBook(incomingBook);
			
			String incomingGoogleApiBookId = book.getGoogleApiBookId();
			boolean isBookForRent = book.isBookForRent();
			boolean isBookForSwap = book.isBookForSwap();
			
			
			
			//verify the book: verification service (synchronous call: book addition is not started until barcode is verified
			/*
			 * we pass the temporary barcode url to the service along with the book id and user added barcode isbn number
			 * the verification service will check if the images are okay to be uploaded
			 * it responds back with a json output showing the status of the verification
			 * using this status if it is success we move forward with the book addition else 
			 * else we call the notification service to inform user that the barcode verification has failed
			 */
			
			log.info("****values coming in the system ::: incomingGoogleApiBookId " + incomingGoogleApiBookId);
			log.info("**** isBookForRent " + isBookForRent + " :: isBookForSwap " + isBookForSwap  ); 
			
			GoogleApiBook googleApiBook = bookAdditionBusinessObject.addingOrUpdatingGoogleApiBook(incomingGoogleApiBookId, isBookForRent, isBookForSwap);
			
			
		    book.setGoogleApiBook(googleApiBook);
		    book.setBookAvailable(true);
		    book.setBookRentedAtThisTime(false);
		    book.setBookSwapped(false);
		    book.setTotalOngoingInteractionsForThisBook(0);
		    
		    log.info("****************** book :: " + book);
		    
		    this.bookService.addBook(book);
		    
		    BookResponseDTO outgoingBook = dtoMappings.mapBookToBookResponseDto(book);
		    
//			Book addedBook = this.bookService.addBook(book);
//			return ResponseEntity.status(HttpStatus.CREATED).body(addedBook);
		    
		    return ResponseEntity.status(HttpStatus.CREATED).body(outgoingBook);
		}catch(Exception e) {
			log.error("error in adding the book");
			e.printStackTrace(); 
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		 
	}
	
}
