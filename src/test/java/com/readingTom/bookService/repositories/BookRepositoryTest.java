package com.readingTom.bookService.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;

import com.readingTom.bookService.entities.Book;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class BookRepositoryTest {

	//For testing we use :: Given When Then
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	private Book book;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("Testing BookRepository...");
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.info("BookRepository tested!!!");
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
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	@DisplayName("junit test to test the book saving operation")
	public void givenBookObject_whenSaved_thenReturnSavedBook() {
		
		Book savedBook = this.bookRepo.save(book);
		log.info("savedBook ID::: " + savedBook.getBookId() + " " + savedBook.isBookAvailable());
		
		assertThat(savedBook).isNotNull();
		assertThat(savedBook.getBookOwnerId()).isEqualTo("111");
		assertTrue(savedBook.isBookForRent());
		assertFalse(savedBook.isBookForSwap());
		
	}
	
	@Test
	@DisplayName("junit test to finnd the book by id")
	void testFindByIdString() {

		
		Book savedBook = this.bookRepo.save(book);
		
		String book1Id = savedBook.getBookId();
		
		Book findBook = this.bookRepo.findById(book1Id).orElseThrow();
		
		assertThat(findBook).isNotNull();
		

	}

	@Test
	void testFindByIncorrectBookOwnerId() {
		
		Book savedBook = this.bookRepo.save(book);
		
		
		Page<Book> findBooksPage = this.bookRepo.findByBookOwnerId("112", null);
		
		List<Book> booksList = findBooksPage.getContent();
		
		assertThat(booksList).isEmpty();	
		assertThat(booksList.size()).isEqualTo(0);
	
	}
	
	@Test
	void testFindByCorrectBookOwnerId() {
		
		Book savedBook = this.bookRepo.save(book);
		
		
		Page<Book> findBooksPage = this.bookRepo.findByBookOwnerId("111", null);
		
		List<Book> booksList = findBooksPage.getContent();
		
		assertThat(booksList).isNotNull();	
		assertThat(booksList.size()).isGreaterThan(1);
	
	}

	@Test
	void testFindByBookOwnerIdAndIsBookAvailableAndIsBookForRent() {
		Book savedBook = this.bookRepo.save(book);
		
		Page<Book> pageBooks =  this.bookRepo.findByBookOwnerIdAndIsBookAvailableAndIsBookForRent("111", true, true, null);
	
		List<Book> booksList = pageBooks.getContent();
		
		assertThat(booksList).isNotNull();
		assertThat(booksList.size()).isGreaterThan(1);
	
	}

//	@Test
//	void testFindByBookOwnerIdAndIsBookAvailableAndIsBookForSwap() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookIdAndIsBookAvailableAndIsBookForRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwap() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerifiedAndBookRentalPricePerDayBetween() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndGoogleApiBookId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForRent() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForSwap() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForRentAndIsBookVerified() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFindByBookOwnerIdAndGoogleApiBookIdAndIsBookAvailableAndIsBookForSwapAndIsBookVerified() {
//		fail("Not yet implemented");
//	}

}
