package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.model.book.BookDto;
import com.biblioteca.np2.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Livros Google", description = "API para busca de livros na Google Books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Operation(summary = "Busca livros na Google Books API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livros encontrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Termo de busca inválido", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro ao consultar Google Books API", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(
            @Parameter(description = "Termo de busca", required = true, example = "java")
            @RequestParam String query,
            
            @Parameter(description = "Número máximo de resultados (1-10)", example = "5")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        
        List<BookDto> books = bookService.searchBooks(query, limit);
        return ResponseEntity.ok(books);
    }
}


