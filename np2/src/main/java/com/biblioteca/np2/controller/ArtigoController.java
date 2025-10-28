package com.biblioteca.np2.controller;


import com.biblioteca.np2.domain.dto.Artigo.ArtigoDto;
import com.biblioteca.np2.domain.dto.Artigo.ArtigoLowDto;
import com.biblioteca.np2.service.ArtigoService;
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

    @PostMapping
    public ArtigoDto create(@RequestBody ArtigoDto artigo){
        return service.create(artigo);
    }

    @GetMapping("/all")
    public List<ArtigoLowDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ArtigoLowDto findLivroById(@PathVariable(name = "id") Integer id){
        return service.findLivroById(id);
    }

    @PutMapping
    public ArtigoDto update(@RequestBody ArtigoDto artigo){
        return service.update(artigo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){
        service.deleteArtigoById(id);
    }
}
