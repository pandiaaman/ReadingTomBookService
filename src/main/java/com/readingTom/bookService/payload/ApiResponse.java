package com.readingTom.bookService.payload;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse { //this is used for exception handling

	private boolean success;
	
	private String msg;
	
	private HttpStatus status;
}
