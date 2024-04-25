package com.readingTom.bookService.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.repositories.BookRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

	@Mock
	private BookRepository bookRepo;
	
	@InjectMocks
	private BookServiceImpl bookServiceImpl;
	
	private Book book;
	
	@Autowired
	private EntityManager entityManager;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("testing book service...");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.info("book service tested!!!");
	}

	@BeforeEach
	void setUp() throws Exception {
		
		book = Book.builder().bookOwnerId("111").googleApiBookId("g25zXEwMYS4C").isBookForRent(true)
				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
				.isBookAvailable(true).build();
	}

	@AfterEach
	@Transactional
	void tearDown() throws Exception {
//		entityManager.flush();
//		entityManager.clear();
	}

	@Test
	@DisplayName("junit to test saving of the book at service layer")
	void GivenBookObject_WhenSaved_ThenReturnBookObject() {
		
		//given: precondition to setup
//		given(bookRepo.findById("asdf")).willReturn(Optional.empty());
//		given(bookRepo.findByBookOwnerId(book.getBookOwnerId(), null)).willReturn(Page.empty());
		
		given(bookRepo.save(book)).willReturn(book);
		
		// when -  action or the behaviour that we are going test
		Book savedBook = bookServiceImpl.addBook(book);
		
		log.info("book saved is :: " + savedBook);
		assertThat(savedBook).isNotNull();
	}

	@Test
	void testGetAllBooks() {
		
//		Book book1 = Book.builder().bookOwnerId("112").googleApiBookId("g25zXEwMYttC").isBookForRent(true)
//				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
//				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
//				.isBookAvailable(true).build();
//		
//		Book book2 = Book.builder().bookOwnerId("212").googleApiBookId("g245XEwMYttC").isBookForRent(true)
//				.isBookForSwap(false).userUploadedBookImage1TemporaryUrl("turl1").userUploadedBookImage2TemporaryUrl("turl2")
//				.userUploadedBookBarcodeImageTemporaryUrl("burl").userUploadedBookDetails("some book details")
//				.isBookAvailable(true).build();
//		
//		given(bookRepo.findAll()).willReturn(List.of(book1, book2));
//		
//		List<Book> allBooks = bookServiceImpl.getAllBooks(null);
//		
//		assertThat(allBooks).isNotNull();
//		assertThat(allBooks.size()).isEqualTo(2);
		
		
		Page<Book> mockPage = mock(Page.class);
		
		List<Book> mockBooks = new ArrayList<>();
		
		when(mockPage.getContent()).thenReturn(mockBooks);
		
		when(bookRepo.findAll(any(Pageable.class))).thenReturn(mockPage);
		
		List<Book> result = bookServiceImpl.getAllBooks(Pageable.unpaged());
		
		verify(bookRepo).findAll(any(Pageable.class));
		
		assertNotNull(result);
		assertEquals(mockBooks.size(), result.size());
		assertSame(mockBooks, result);
		
	}
//
//	@Test
//	void testGetBooksByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBooksByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBooksByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetBookById() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddBook() {
//		fail("Not yet implemented");
//	}

}
