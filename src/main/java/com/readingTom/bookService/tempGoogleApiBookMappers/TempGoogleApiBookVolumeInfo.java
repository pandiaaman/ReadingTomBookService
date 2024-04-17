package com.readingTom.bookService.tempGoogleApiBookMappers;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TempGoogleApiBookVolumeInfo {

	private String title;
	private String subtitle;
	private String description;
	private String publisher;
	private String publishedDate;
	private int pageCount;
	private String maturityRating;
	private List<TempISBNObject> industryIdentifiers;
	private TempGoogleApiBookImageLinks imageLinks;
	private String language;
	private ArrayList<String> authors;
	private ArrayList<String> categories;
}