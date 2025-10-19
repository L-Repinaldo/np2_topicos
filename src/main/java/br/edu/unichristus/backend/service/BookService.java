package br.edu.unichristus.backend.service;

import br.edu.unichristus.backend.client.GoogleBooksClient;
import br.edu.unichristus.backend.domain.model.book.BookDTO;
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
    
    public List<BookDTO> getBooks(String query, Integer limit) {
        log.info("BookService: Iniciando busca de livros - query: '{}', limit: {}", query, limit);
        
        // Validar palavra-chave
        if (query == null || query.trim().isEmpty()) {
            log.error("BookService: Termo de busca vazio ou nulo");
            throw new IllegalArgumentException("termo de busca não pode estar vazio");
        }
        
        // Definir limite padrão se necessário
        int finalLimit = (limit == null || limit < 1) ? DEFAULT_LIMIT : limit;
        log.info("BookService: Usando limite de {} resultados", finalLimit);
        
        try {
            log.info("BookService: Chamando GoogleBooksClient.searchBooks()");
            List<BookDTO> books = googleBooksClient.searchBooks(query.trim(), finalLimit);
            
            log.info("BookService: Retornando {} livros encontrados", books.size());
            return books;
            
        } catch (Exception e) {
            log.error("BookService: Erro ao consultar Google Books API: {}", e.getMessage(), e);
            throw new RuntimeException("erro ao consultar Google Books API", e);
        }
    }
}
