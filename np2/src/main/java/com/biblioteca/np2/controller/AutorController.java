package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Autor.AutorDto;
import com.biblioteca.np2.domain.dto.Autor.AutorLowDto;
import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.dto.Obras;
import com.biblioteca.np2.service.AutorService;
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
@RequestMapping("/api/v1/autor")
@Tag(name = "Autores", description = "API para gerenciamento de autores")
public class AutorController {

    @Autowired
    private AutorService service;

    @Operation(summary = "Cadastra um novo autor")
    @ApiResponse(responseCode = "200", description = "Autor cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou autor já existe")
    @PostMapping
    public AutorDto create(@RequestBody AutorDto dto){ return service.create(dto); }

    @Operation(summary = "Lista todos os autores cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso")
    @GetMapping("/all")
    public List<AutorLowDto> getAll(){return service.getAll();}

    @Operation(summary = "Busca autor pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public AutorLowDto getAutorById(@PathVariable(name = "id") Integer id){return service.findAutorById(id);}

    @Operation(summary = "Atualiza dados de um autor")
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public AutorDto update(@RequestBody AutorDto dto){return service.update(dto);}

    @Operation(summary = "Remove um autor")
    @ApiResponse(responseCode = "200", description = "Autor removido com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){ service.deleteAutorById(id);}

    // #### Métodos API ####

    @Operation(summary = "Lista todos livros cadastrados pertencentes a esse autor")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping("/{nome}/livros")
    public List<? extends Obras> getLivrosByAutor(@PathVariable(name = "nome") String nome){return service.getLivrosPorAutor(nome);}

    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a esse autor")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/{nome}/artigos")
    public List<? extends Obras> getArtigosByAutor(@PathVariable(name = "nome") String nome){return service.getArtigosPorAutor(nome);}

    @Operation(summary = "Lista todas as obras cadastradas pertencentes a esse autor")
    @ApiResponse(responseCode = "200", description = "Lista de obras retornada com sucesso")
    @GetMapping("/{nome}/obras")
    public List<Object> getObrasByAutor(@PathVariable(name = "nome") String nome){return service.getObrasPorAutor(nome);}

}
