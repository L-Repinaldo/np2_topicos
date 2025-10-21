package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Autor.AutorDto;
import com.biblioteca.np2.domain.dto.Autor.AutorLowDto;
import com.biblioteca.np2.service.AutorService;
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

    @PostMapping
    public AutorDto create(@RequestBody AutorDto dto){ return service.create(dto); }

    @GetMapping("/all")
    public List<AutorLowDto> getAll(){return service.getAll();}

    @GetMapping("/{id}")
    public AutorLowDto getAutorById(@PathVariable(name = "id") Integer id){return service.findAutorById(id);}

    @PutMapping
    public AutorDto update(@RequestBody AutorDto dto){return service.update(dto);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){ service.deleteAutorById(id);}
}
