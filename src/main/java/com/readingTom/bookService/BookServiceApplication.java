package com.readingTom.bookService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
		
		System.out.println("************************");
		System.out.println("STARTED BOOK SERVICE...");
		System.out.println("************************");
	}

}
