package com.biblioteca.np2.service;

import com.biblioteca.np2.client.GoogleBooksClient;
import com.biblioteca.np2.domain.model.book.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private static final int DEFAULT_LIMIT = 5;
    
    @Autowired
    private GoogleBooksClient googleBooksClient;
    
    public List<BookDto> searchBooks(String query, Integer limit) {
        log.info("BookService: Buscando livros - query: '{}', limit: {}", query, limit);
        
        if (query == null || query.trim().isEmpty()) {
            log.error("BookService: Termo de busca vazio");
            throw new IllegalArgumentException("Termo de busca não pode estar vazio");
        }
        
        int finalLimit = (limit == null || limit < 1) ? DEFAULT_LIMIT : limit;
        
        List<BookDto> books = googleBooksClient.searchBooks(query.trim(), finalLimit);
        log.info("BookService: Retornando {} livros", books.size());
        
        return books;
    }
}


