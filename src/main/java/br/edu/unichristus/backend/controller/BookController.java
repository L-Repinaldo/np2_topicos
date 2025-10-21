package br.edu.unichristus.backend.controller;

import br.edu.unichristus.backend.domain.dto.ErrorDTO;
import br.edu.unichristus.backend.domain.model.book.BookDTO;
import br.edu.unichristus.backend.service.BookService;
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
@Tag(name = "Books", description = "API para busca de livros")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    //resumo e tag
    @Operation(summary = "busca livros usando a Google Books API com base em um termo de pesquisa.", tags = "Books")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de livros encontrados com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Termo de busca invalido ou vazio", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno ao consultar Google Books API", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDTO.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooks(
            @Parameter(description = "Termo de busca para encontrar livros", required = true, example = "musculacao")
            @RequestParam String query,
            
            @Parameter(description = "Numero maximo de resultados (1-10)", example = "5")
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        
        List<BookDTO> books = bookService.getBooks(query, limit);
        return ResponseEntity.ok(books);
    }
}
