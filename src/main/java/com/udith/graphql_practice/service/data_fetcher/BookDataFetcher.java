package com.udith.graphql_practice.service.data_fetcher;

import com.udith.graphql_practice.model.Book;
import com.udith.graphql_practice.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

public class BookDataFetcher implements DataFetcher<Book>{

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment dataFetchingEnvironment){
        String isn = dataFetchingEnvironment.getArgument("id");
        return bookRepository.findOne(isn);
    }   

}