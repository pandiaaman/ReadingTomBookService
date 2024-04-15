package com.readingTom.bookService.customException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4823310254712920722L;

	public ResourceNotFoundException(String msg, String resource) {
		super(msg);
		log.error("ERROR::: " + resource + " not found on server exception");
	}
	
}
