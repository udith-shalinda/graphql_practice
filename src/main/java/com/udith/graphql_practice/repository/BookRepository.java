package com.udith.graphql_practice.repository;

import com.udith.graphql_practice.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findByIsn(String isn);
}