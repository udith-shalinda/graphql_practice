package com.udith.graphql_practice.service;


import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import com.udith.graphql_practice.model.Book;
import com.udith.graphql_practice.repository.BookRepository;
import com.udith.graphql_practice.service.data_fetcher.AllBooksDataFetcher;
import com.udith.graphql_practice.service.data_fetcher.BookDataFetcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;


@Service
public class GraphqlService{

    @Value("classpath:books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;
    @Autowired
    private BookRepository bookRepository;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {
        loadDataIntoHSQL();

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring(){
        return RuntimeWiring.newRuntimeWiring()
        .type("Query", typeWiring-> typeWiring
            .dataFetcher("allBooks", allBooksDataFetcher)
            .dataFetcher("book",bookDataFetcher))
        .build();  
    }
    private void loadDataIntoHSQL(){
        Stream.of(
            new Book("123","name 1","Kindle Edition 1",
                new String[]{
                    "chole decker 3",
                    "sfsfs sfsfs"
                },
                "Nov 2013 1" 
            ),
            new Book("124","name 2","Kindle Edition 2",
                new String[]{
                    "chole decker 2",
                    "sfsfs sfsfs"
                },
                "Nov 2013 2" 
            ),
            new Book("125","name 3","Kindle Edition 3",
            new String[]{
                "chole decker 3",
                "sfsfs sfsfs"
            },
            "Nov 2013 3" 
        )
        ).forEach(book->{
            bookRepository.save(book);
        });
    }

    public GraphQL getGraphQL(){
        return graphQL;
    }


}