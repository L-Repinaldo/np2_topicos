package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Livro.LivroDto;
import com.biblioteca.np2.domain.dto.Livro.LivroLowDto;
import com.biblioteca.np2.service.LivroService;
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


    @PostMapping
    public LivroDto create(@RequestBody LivroDto livro){
        return service.create(livro);
    }

    @GetMapping("/all")
    public List<LivroLowDto> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public LivroLowDto findLivroById(@PathVariable(name = "id") Integer id){
        return service.findLivroById(id);
    }

    @PutMapping
    public LivroDto update(@RequestBody LivroDto livro){
        return service.update(livro);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){
        service.deleteLivroById(id);
    }
}
