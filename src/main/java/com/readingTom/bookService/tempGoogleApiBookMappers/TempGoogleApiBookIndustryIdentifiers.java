package com.readingTom.bookService.tempGoogleApiBookMappers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TempGoogleApiBookIndustryIdentifiers {
	private List<TempISBNObject> isbnArray;
}

