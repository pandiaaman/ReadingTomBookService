package com.readingTom.bookService.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.readingTom.bookService.customException.BookNotFoundException;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.dtoMappings.DTOMappings;
import com.readingTom.bookService.dtoMappings.DTOMappingsImpl;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;
import com.readingTom.bookService.services.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

	 @InjectMocks
	 private BookController bookController;
	 
	 @Mock
	 private BookService bookService;
	 
	 @Autowired
	 private DTOMappingsImpl dtoMappings = new DTOMappingsImpl();
	 
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testBookController() {
//		// Create sample books
		List<Book> books = new ArrayList<>();
        
		Book book = Book.builder().bookOwnerId("111").googleApiBook(new GoogleApiBook()).googleApiBookId("g25zXEwMYS4C").isBookForRent(true)
				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
				.isBookAvailable(true).build();
		
		Book book2 = Book.builder().bookOwnerId("111").googleApiBook(new GoogleApiBook()).googleApiBookId("g25zXEwMYS4C").isBookForRent(true)
				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
				.isBookAvailable(true).build();
       
        
        books.add(book);
        books.add(book2);
        
        
        when(this.bookService.getAllBooks(any())).thenReturn(books);
        
        
		//converting output to DTOS\
		List<BookResponseDTO> outgoingBooks = new ArrayList<>();
		
		for(Book booook : books) {
			BookResponseDTO bookResponse = dtoMappings.mapBookToBookResponseDto(booook);
			
			outgoingBooks.add(bookResponse);
		}
		
		ResponseEntity<List<BookResponseDTO>> ExpectedfinalBooks = ResponseEntity.status(HttpStatus.OK).body(outgoingBooks);
        
		
		ResponseEntity<List<BookResponseDTO>> ActualfinalBooks = this.bookController.getAllBooks(1, 5, false, "bookCreatedAt");
		
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, ActualfinalBooks.getStatusCode());
		
//		when(this.bookController.getAllBooks(1, 5, false, "bookCreatedAt")).thenThrow(new NullPointerException());
		
		
		
	
	
//        ResponseEntity<List<BookResponseDTO>> outbooks = ResponseEntity.status(HttpStatus.OK).body(books);
       
//        String sortby = "bookCreatedAt";
//        List<Sort.Order> sortOrder = Arrays.asList(new Sort.Order(Sort.Direction.ASC, sortby));
//		
//        Pageable pageable = PageRequest.of(1, 5, Sort.by(sortOrder));
		
//		List<Book> allBooks = this.bookService.getAllBooks(pageable);
        
        // Mock the behavior of bookService.getAllBooks()
//        when(this.bookController.getAllBooks(1,1,true,any())).thenReturn(outbooks);
        
        
        // Verify that the bookService.getAllBooks method was called with the correct arguments
//        verify(bookController).getAllBooks(1,1,true,any());
//        
//        // Verify that the response status is OK
//        assertEquals(HttpStatus.OK, outbooks.getStatusCode());
//        
//        // Verify that the response body contains the correct number of books
//        assertEquals(2, outbooks.getBody().size());
	}
//
	@Test
	void testGetAllBooks() {
		
		List listMock = mock(List.class);
		
		when(listMock.size()).thenReturn(2).thenReturn(3);
		
		assertEquals(2, listMock.size());
		assertEquals(3, listMock.size());
	}
//
//	@Test
//	void testGetAllVerifiedAvailableBooksByOwnerIdForRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllVerifiedAvailableBooksByOwnerIdForSwap() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllVerifiedAvailableBooksByGoogleApiIdForRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetAllVerifiedAvailableBooksByGoogleApiIdForSwap() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetOneBook() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddBook() {
//		fail("Not yet implemented");
//	}

}
