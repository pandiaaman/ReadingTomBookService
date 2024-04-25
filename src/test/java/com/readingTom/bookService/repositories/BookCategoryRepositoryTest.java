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

import com.readingTom.bookService.entities.BookCategory;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
class BookCategoryRepositoryTest {

	@Autowired
	private BookCategoryRepository bookCategoryRepo;
	
	@Autowired
	private EntityManager entityManager;
	
	private BookCategory category;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		log.info("testing category repo...");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		log.info("book category repo tested!!!");
	}

	@BeforeEach
	void setUp() throws Exception {
		
		category = BookCategory.builder().categoryName("some category").bookCategoryCreatedAt(LocalDateTime.now())
				.bookCategoryUpdatedAt(LocalDateTime.now()).build();
	}

	@AfterEach
	@Transactional
	void tearDown() throws Exception {
		entityManager.flush();
		entityManager.clear();
	}

	@Test
	void testFindByCategoryName() {
		
		BookCategory savedCategory = this.bookCategoryRepo.save(category);
		
		assertThat(savedCategory.getCategoryName()).isEqualTo("some category");
	}

}
