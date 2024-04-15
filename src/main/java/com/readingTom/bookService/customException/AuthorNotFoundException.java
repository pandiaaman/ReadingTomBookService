package com.readingTom.bookService.customException;




public class AuthorNotFoundException extends ResourceNotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6013175315283696817L;

	public AuthorNotFoundException(String msg) {
		super(msg, "book author");
		
	}

}
