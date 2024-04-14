package com.readingTom.bookService.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.readingTom.bookService.customException.ResourceNotFoundException;
import com.readingTom.bookService.payload.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class BookExceptionAdvisor {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> handleNoProductFoundException(ResourceNotFoundException ex){
		log.warn("EXCEPTION OCCURED:::::::handling the no book found exeption");
		
		ApiResponse res =  ApiResponse.builder()
				.msg(ex.getMessage())
				.status(HttpStatus.NO_CONTENT)
				.success(false)
				.build();
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
}
