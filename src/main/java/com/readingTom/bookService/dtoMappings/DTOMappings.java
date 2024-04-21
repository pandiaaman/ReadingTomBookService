package com.readingTom.bookService.dtoMappings;


import com.readingTom.bookService.dto.BookAuthorResponseDTO;
import com.readingTom.bookService.dto.BookCategoryResponseDTO;
import com.readingTom.bookService.dto.BookRequestDTO;
import com.readingTom.bookService.dto.BookResponseDTO;
import com.readingTom.bookService.dto.GoogleApiBookResponseDTO;
import com.readingTom.bookService.entities.Book;
import com.readingTom.bookService.entities.BookAuthor;
import com.readingTom.bookService.entities.BookCategory;
import com.readingTom.bookService.entities.GoogleApiBook;

public interface DTOMappings {

	//BookRequestDTO to Book
	public Book mapBookRequestDtoToBook(BookRequestDTO bookRequestDto);
	
	//Book to BookResponseDTO
	public BookResponseDTO mapBookToBookResponseDto(Book book);
	
	//GoogleApiBook to GoogleApiBookResponseDTO
	public GoogleApiBookResponseDTO mapGoogleApiBookToGoogleApiBookResponseDTO(GoogleApiBook googleApiBook);
	
	public BookAuthorResponseDTO mapBookAuthorToBookAuthorResponseDTO(BookAuthor bookAuthor);
	
	public BookCategoryResponseDTO mapBookCategoryTOBookCategoryResponseDTO(BookCategory bookCategory);
	
}
