package com.readingTom.bookService.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.GoogleApiBook;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class GoogleApiBookRepositoryTest {

	@Autowired
	private GoogleApiBookRepository googleApiBookRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	private GoogleApiBook gBook;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("testing googleApiBookRepo...");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.info("googleApiBookRepo tested!!!");
	}

	@BeforeEach
	void setUp() throws Exception {
		
		BookAuthor bookAuthor = new BookAuthor();
		bookAuthor.setAuthorName("Paulo Coelho");
		
		List<BookAuthor> authors = new ArrayList<>();
		authors.add(bookAuthor);
		
		gBook = GoogleApiBook.builder().googleApiBookId("111").googleApiBookTitle("The Alchemist").googleApiBookSubTitle("some subtitle")
				.googleApiBookCreatedAt(LocalDateTime.now()).googleApiBookPublishedDate("12/10/2021").googleApiBookPublisher("some").
				googleApiBookAuthors(authors).build();
	}

	@AfterEach
	@Transactional
	void tearDown() throws Exception {
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	void givenApiBook_WhenSaved_ThentestSavedBook() {
		
		GoogleApiBook savedBook = this.googleApiBookRepo.save(gBook);
		
		assertThat(savedBook.getGoogleApiBookTitle()).isEqualTo("The Alchemist");
	}

}
