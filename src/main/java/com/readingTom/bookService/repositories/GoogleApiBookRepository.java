package com.readingTom.bookService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.readingTom.bookService.entities.GoogleApiBook;

@Repository
public interface GoogleApiBookRepository extends JpaRepository<GoogleApiBook, String> {

}
