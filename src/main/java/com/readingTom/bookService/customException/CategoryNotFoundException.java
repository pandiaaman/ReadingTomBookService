package com.readingTom.bookService.customException;



public class CategoryNotFoundException extends ResourceNotFoundException {/**
	 * 
	 */
	private static final long serialVersionUID = -5834163747886555860L;

	public CategoryNotFoundException(String msg) {
		super(msg, "book category");
		
	}

}
