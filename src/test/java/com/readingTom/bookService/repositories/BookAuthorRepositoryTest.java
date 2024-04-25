package com.readingTom.bookService.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.readingTom.bookService.entities.BookAuthor;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class BookAuthorRepositoryTest {

	@Autowired
	private BookAuthorRepository bookAuthorRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	private BookAuthor author;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("testing author repo...");
	}
	

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.info("author repo tested!!!");
	}

	@BeforeEach
	void setUp() throws Exception {
		author = BookAuthor.builder().authorName("some author").bookAuthorCreatedAt(LocalDateTime.now())
				.bookAuthorUpdatedAt(LocalDateTime.now()).build();
	}

	@AfterEach
	@Transactional
	void tearDown() throws Exception {
		entityManager.flush();
		entityManager.clear();
	}
	

	@Test
	void testFindByAuthorName() {
		
		BookAuthor savedAuthor = this.bookAuthorRepo.save(author);
		
		assertThat(savedAuthor.getAuthorName()).isEqualTo("some author");
	}

}
