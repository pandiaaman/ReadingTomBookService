package com.readingTom.bookService.tempGoogleApiBookMappers;


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
public class TempGoogleApiBook {

	private String id;
	private String etag;
	private String selfLink;
	private TempGoogleApiBookVolumeInfo volumeInfo;
	private TempGoogleApiBookSaleInfo saleInfo;
}
