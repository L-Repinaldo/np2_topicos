package br.edu.unichristus.backend.controller;

import br.edu.unichristus.backend.domain.model.book.BookDTO;
import br.edu.unichristus.backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Books", description = "API para busca de livros")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping("/search")
    @Operation(summary = "Buscar livros", description = "Busca livros usando a Google Books API")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @Parameter(description = "Termo de busca", required = true, example = "musculacao")
            @RequestParam String query,
            
            @Parameter(description = "Número máximo de resultados (1-10)", example = "5")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        
        List<BookDTO> books = bookService.getBooks(query, limit);
        return ResponseEntity.ok(books);
    }
}
