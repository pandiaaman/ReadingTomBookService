package com.readingTom.bookService.customException;




public class BookNotFoundException extends ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5356171910895502825L;
	
	public BookNotFoundException(String msg) {
		super(msg, "book");
	}

}
