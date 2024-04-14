package com.readingTom.bookService.customException;

public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4823310254712920722L;

	public ResourceNotFoundException(String msg) {
		super(msg);
		System.out.println("resource not found on server exception");
	}
	
}
