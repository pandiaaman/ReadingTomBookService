package com.readingTom.bookService.customException;




public class GoogleApiBookNotFoundException extends ResourceNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6649173389027468611L;
	
	public GoogleApiBookNotFoundException(String msg) {
		super(msg,"google api book");
		
	}

}
