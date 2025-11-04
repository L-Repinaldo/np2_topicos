package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Editora.EditoraDto;
import com.biblioteca.np2.domain.dto.Editora.EditoraLowDto;
import com.biblioteca.np2.domain.dto.ErrorDto;
import com.biblioteca.np2.domain.dto.Obras;
import com.biblioteca.np2.service.EditoraService;
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
@RequestMapping("/api/v1/editora")
@Tag(name = "Editoras", description = "API para gerenciamento de editoras")
public class EditoraController {

    @Autowired
    private EditoraService service;

    @Operation(summary = "Cadastra uma nova editora")
    @ApiResponse(responseCode = "200", description = "Editora cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou editoora já existe")
    @PostMapping
    public EditoraDto create(@RequestBody EditoraDto dto){return service.create(dto);}

    @Operation(summary = "Lista todas as editoras cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de editoras retornada com sucesso")
    @GetMapping("/all")
    public List<EditoraLowDto> getALL(){ return service.getAll();}

    @Operation(summary = "Busca editora pela ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Editora encontrada"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDto.class)))
    })
    @GetMapping("/{id}")
    public EditoraLowDto getEditoraById(@PathVariable(name = "id") Integer id){return service.findEditoraById(id);}

    @Operation(summary = "Atualiza dados de uma editora")
    @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PutMapping
    public EditoraDto update(@RequestBody EditoraDto dto){return service.update(dto);}

    @Operation(summary = "Remove uma editora")
    @ApiResponse(responseCode = "200", description = "Editora removida com sucesso")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){service.deleteEditoraById(id);}


    // #### Métodos API ####

    @Operation(summary = "Lista todos livros cadastrados pertencentes a essa editora")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping("/{nome}/livros")
    public List<? extends Obras> getLivrosByEditora(@PathVariable(name = "nome") String nome){return service.getLivrosByEditora(nome);}

    @Operation(summary = "Lista todos os artigos cadastrados pertencentes a essa editora")
    @ApiResponse(responseCode = "200", description = "Lista de artigos retornada com sucesso")
    @GetMapping("/{nome}/artigos")
    public List<? extends Obras> getArtigosByCategoria(@PathVariable(name = "nome") String nome){return service.getArtigosByEditora(nome);}

    @Operation(summary = "Lista todas as obras cadastradas pertencentes a essa editora")
    @ApiResponse(responseCode = "200", description = "Lista de obras retornada com sucesso")
    @GetMapping("/{nome}/obras")
    public ArrayList<Object> getObrasByCategoria(@PathVariable(name = "nome") String nome){return service.getObrasByEditora(nome);}

}
