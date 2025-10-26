package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Editora.EditoraDto;
import com.biblioteca.np2.domain.dto.Editora.EditoraLowDto;
import com.biblioteca.np2.service.EditoraService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/editora")
@Tag(name = "Editoras", description = "API para gerenciamento de editoras")
public class EditoraController {

    @Autowired
    private EditoraService service;

    @PostMapping
    public EditoraDto create(@RequestBody EditoraDto dto){return service.create(dto);}

    @GetMapping("/all")
    public List<EditoraLowDto> getALL(){ return service.getAll();}

    @GetMapping("/{id}")
    public EditoraLowDto getEditoraById(@PathVariable(name = "id") Integer id){return service.findEditoraById(id);}

    @PutMapping
    public EditoraDto update(@RequestBody EditoraDto dto){return service.update(dto);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){service.deleteEditoraById(id);}

}
