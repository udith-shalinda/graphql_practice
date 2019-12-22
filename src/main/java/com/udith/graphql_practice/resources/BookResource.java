package com.udith.graphql_practice.resources;

import com.udith.graphql_practice.service.GraphQLService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import graphql.ExecutionResult;

@RestController
@RequestMapping("/rest")
public class BookResource{

    @Autowired
    GraphQLService graphQLServie;

    @PostMapping("/books")
    public ResponseEntity<Object> getAllBooks(@RequestBody String query){
        ExecutionResult execute =  graphQLServie.getGraphQL().execute(query);
        return new ResponseEntity<Object>(execute,HttpStatus.OK);
    }
}