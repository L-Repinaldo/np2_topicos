package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Categoria.CategoriaDto;
import com.biblioteca.np2.domain.dto.Categoria.CategoriaLowDto;
import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.dto.Obras;
import com.biblioteca.np2.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")
@Tag(name = "Categorias", description = "API para gerenciamento de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @Operation(summary = "Cadastra uma nova categoria")
    @ApiResponse(responseCode = "200", description = "Categoria cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou categoria já existe")
    @PostMapping
    public CategoriaDto create(@RequestBody CategoriaDto dto){return service.create(dto);}

    @Operation(summary = "Lista todos as categorias cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso")
    @GetMapping("/all")
    public List<CategoriaLowDto> getAll(){ return service.getAll();}

    @Operation(summary = "Busca categoria pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public CategoriaLowDto getCategoriaById(@PathVariable(name = "id") Integer id){return service.findCategoriaById(id);}

    @Operation(summary = "Atualiza dados de uma categoria")
    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public CategoriaDto update(@RequestBody CategoriaDto dto){return service.update(dto);}

    @Operation(summary = "Remove um categoria")
    @ApiResponse(responseCode = "200", description = "Categoria removida com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){service.deleteCategoriaById(id);}

    // #### Métodos API ####

    @Operation(summary = "Lista todos livros cadastrados pertencentes a essa categoria")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping("/{nome}/livros")
    public List<? extends Obras> getLivrosByCategoria(@PathVariable(name = "nome") String nome){return service.getLivrosByCategoria(nome);}

    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a essa categoria")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/{nome}/artigos")
    public List<? extends Obras> getArtigosByCategoria(@PathVariable(name = "nome") String nome){return service.getArtigosByCategoria(nome);}

    @Operation(summary = "Lista todas as obras cadastradas pertencentes a essa categoria")
    @ApiResponse(responseCode = "200", description = "Lista de obras retornada com sucesso")
    @GetMapping("/{nome}/obras")
    public ArrayList<Object> getObrasByCategoria(@PathVariable(name = "nome") String nome){return service.getObrasByCategoria(nome);}
}
