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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@Tag(name = "Livros Google", description = "API para gerenciamento de livros e integração com Google Books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    // ==================== CRUD DE LIVROS SALVOS ====================
    
    @Operation(summary = "Cadastrar um novo livro no sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @PostMapping
    public ResponseEntity<BookDto> create(@RequestBody BookDto bookDto) {
        BookDto createdBook = bookService.create(bookDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    @Operation(summary = "Listar todos os livros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso!")
    @GetMapping
    public ResponseEntity<List<BookDto>> findAll() {
        List<BookDto> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }
    
    @Operation(summary = "Buscar livro por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> findById(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id) {
        BookDto book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }
    
    @Operation(summary = "Atualizar um livro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDto> update(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody BookDto bookDto) {
        BookDto updatedBook = bookService.update(id, bookDto);
        return ResponseEntity.ok(updatedBook);
    }
    
    @Operation(summary = "Remover um livro do sistema")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro removido com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Listar livros favoritos")
    @ApiResponse(responseCode = "200", description = "Lista de favoritos retornada com sucesso!")
    @GetMapping("/favorites")
    public ResponseEntity<List<BookDto>> findFavorites() {
        List<BookDto> favorites = bookService.findFavorites();
        return ResponseEntity.ok(favorites);
    }
    
    // ==================== BUSCA GOOGLE BOOKS API ====================
    
    @Operation(summary = "Buscar livros na Google Books API")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de livros encontrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Termo de busca inválido ou vazio", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar Google Books API", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchGoogleBooks(
            @Parameter(description = "Termo de busca para encontrar livros", required = true, example = "musculacao")
            @RequestParam String query,
            
            @Parameter(description = "Número máximo de resultados (1-10)", example = "5")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        
        List<BookDto> books = bookService.searchGoogleBooks(query, limit);
        return ResponseEntity.ok(books);
    }
}


