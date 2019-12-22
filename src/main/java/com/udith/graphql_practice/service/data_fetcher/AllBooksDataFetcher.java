package com.udith.graphql_practice.service.data_fetcher;

import java.util.List;

import com.udith.graphql_practice.model.Book;
import com.udith.graphql_practice.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>>{

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment){
        return bookRepository.findAll();
    }
}