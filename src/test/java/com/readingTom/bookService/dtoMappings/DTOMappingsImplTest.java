package com.readingTom.bookService.dtoMappings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.readingTom.bookService.dto.BookRequestDTO;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.GoogleApiBook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class DTOMappingsImplTest {

	@Autowired
	private DTOMappingsImpl dtoMapping;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dtoMapping = new DTOMappingsImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMapBookRequestDtoToBook() {
		
		log.info("testing the book mapping: mapping of bookRequestDTO to book");
		
		BookRequestDTO bookRequestDto = new BookRequestDTO();
		bookRequestDto.setBookOwnerId("1");
		bookRequestDto.setGoogleApiBookId("aer2345dfg");
		bookRequestDto.setBookForRent(true);
		bookRequestDto.setBookForSwap(false);
		bookRequestDto.setBookRentalPricePerDay(20);
		bookRequestDto.setBookRentalPricePerDayCurrency("INR");
		bookRequestDto.setUserUploadedBookBarcodeImageTemporaryUrl("tempurl");
		bookRequestDto.setUserUploadedBookDetails("asdf");
		bookRequestDto.setUserUploadedBookImage1TemporaryUrl("");
		bookRequestDto.setUserUploadedBookImage2TemporaryUrl("");
		
		Book book = dtoMapping.mapBookRequestDtoToBook(bookRequestDto);
		
		assertThat(book).isNotNull();
		assertEquals(bookRequestDto.getBookOwnerId(), book.getBookOwnerId());
		assertEquals(bookRequestDto.getUserUploadedBookDetails(), book.getUserUploadedBookDetails());
		
	}
//
	@Test
	void testMapBookToBookResponseDto() {
		
		//Given
		Book book = Book.builder().bookOwnerId("111").googleApiBook(new GoogleApiBook()).googleApiBookId("g25zXEwMYS4C").isBookForRent(true)
				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
				.isBookAvailable(true).build();
		
		//When
		BookResponseDTO bookResponse = dtoMapping.mapBookToBookResponseDto(book);
		
		
		//Then
		assertThat(bookResponse).isNotNull();
		
		assertEquals(bookResponse.getBookOwnerId(), book.getBookOwnerId());
	}

//	@Test
//	void testMapGoogleApiBookToGoogleApiBookResponseDTO() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFetchIdOfAllBooksUploadedForThisGoogleApiBook() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrepareBookAuthorListForGoogleApiBook() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testPrepareBookCategoryListForGoogleApiBook() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testMapBookAuthorToBookAuthorResponseDTO() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testMapBookCategoryTOBookCategoryResponseDTO() {
//		fail("Not yet implemented");
//	}

}
