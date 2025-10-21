package br.edu.unichristus.backend.books.controller;

import br.edu.unichristus.backend.books.dto.BookDTO;
import br.edu.unichristus.backend.books.service.BookService;
import br.edu.unichristus.backend.domain.dto.ErrorDTO;
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
@Tag(name = "Books", description = "API para gerenciamento de livros e integração com Google Books")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Operation(summary = "Cadastrar um novo livro no sistema", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PostMapping
    public ResponseEntity<BookDTO> create(@RequestBody BookDTO bookDTO) {
        BookDTO createdBook = bookService.create(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }
    
    @Operation(summary = "Listar todos os livros cadastrados", tags = "Books")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso!")
    @GetMapping
    public ResponseEntity<List<BookDTO>> findAll() {
        List<BookDTO> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }
    
    @Operation(summary = "Buscar livro por ID", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findById(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id) {
        BookDTO book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }
    
    @Operation(summary = "Atualizar um livro existente", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody BookDTO bookDTO) {
        BookDTO updatedBook = bookService.update(id, bookDTO);
        return ResponseEntity.ok(updatedBook);
    }
    
    @Operation(summary = "Remover um livro do sistema", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro removido com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do livro", required = true, example = "1")
            @PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Buscar livros na Google Books API", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de livros encontrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Termo de busca inválido ou vazio", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar Google Books API", 
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchGoogleBooks(
            @Parameter(description = "Termo de busca para encontrar livros", required = true, example = "musculacao")
            @RequestParam String query,
            
            @Parameter(description = "Número máximo de resultados (1-10)", example = "5")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        
        List<BookDTO> books = bookService.searchGoogleBooks(query, limit);
        return ResponseEntity.ok(books);
    }
}

