package com.biblioteca.np2.controller;


import com.biblioteca.np2.domain.dto.Artigo.ArtigoDto;
import com.biblioteca.np2.domain.dto.Artigo.ArtigoLowDto;
import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.service.ArtigoService;
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
@RequestMapping("/api/v1/artigo")
@Tag(name = "Artigos", description = "API para gerenciamento de artigos da biblioteca")
public class ArtigoController {

    @Autowired
    private ArtigoService  service;

    @Operation(summary = "Cadastra um novo artigo")
    @ApiResponse(responseCode = "200", description = "Artigo cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou artigo já existe")
    @PostMapping
    public ArtigoDto create(@RequestBody ArtigoDto artigo){
        return service.create(artigo);
    }

    @Operation(summary = "Lista todos os artigos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/all")
    public List<ArtigoLowDto> getAll(){
        return service.getAll();
    }

    @Operation(summary = "Busca artigo pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Artigo encontrado"),
            @ApiResponse(responseCode = "404", description = "Artigo não encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public ArtigoLowDto findLivroById(@PathVariable(name = "id") Integer id){
        return service.findLivroById(id);
    }

    @Operation(summary = "Atualiza dados de um artigo")
    @ApiResponse(responseCode = "200", description = "Artigo atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public ArtigoDto update(@RequestBody ArtigoDto artigo){
        return service.update(artigo);
    }

    @Operation(summary = "Remove um artigo")
    @ApiResponse(responseCode = "200", description = "Artigo removido com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){
        service.deleteArtigoById(id);
    }

    //#### Métodos API ####


    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a esse autor")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/autor/{nome}")
    public List<ArtigoLowDto> getArtigosByAutor(@PathVariable(name = "nome") String nome){return service.findArtigosByAutor(nome);}

    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a essa editora")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/editora/{nome}")
    public List<ArtigoLowDto> getArtigosByEditora(@PathVariable(name = "nome") String nome){return service.findArtigosByEditora(nome);}

    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a essa categoria")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/categoria/{nome}")
    public List<ArtigoLowDto> getArtigosByCategoria(@PathVariable(name = "nome") String nome){return service.findArtigosByCategoria(nome);}
}
