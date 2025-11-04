package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.dto.Livro.LivroDto;
import com.biblioteca.np2.domain.dto.Livro.LivroLowDto;
import com.biblioteca.np2.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/livro")
@Tag(name = "Livros", description = "API para gerenciamento de livros da biblioteca")
public class LivroController {

    @Autowired
    private LivroService service;

    @Operation(summary = "Cadastra um novo livro")
    @ApiResponse(responseCode = "200", description = "Livro cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou livro já existe")
    @PostMapping
    public LivroDto create(@RequestBody LivroDto livro){
        return service.create(livro);
    }

    @Operation(summary = "Lista todos os livros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping("/all")
    public List<LivroLowDto> getAll(){
        return service.getAll();
    }


    @Operation(summary = "Busca livro pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public LivroLowDto findLivroById(@PathVariable(name = "id") Integer id){
        return service.findLivroById(id);
    }

    @Operation(summary = "Atualiza dados de um livro")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public LivroDto update(@RequestBody LivroDto livro){
        return service.update(livro);
    }

    @Operation(summary = "Remove um livro")
    @ApiResponse(responseCode = "200", description = "Livro removido com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){
        service.deleteLivroById(id);
    }
}
