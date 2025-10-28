package com.biblioteca.np2.controller;

import com.biblioteca.np2.domain.dto.Categoria.CategoriaDto;
import com.biblioteca.np2.domain.dto.Categoria.CategoriaLowDto;
import com.biblioteca.np2.domain.dto.Obras;
import com.biblioteca.np2.service.CategoriaService;
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

    @PostMapping
    public CategoriaDto create(@RequestBody CategoriaDto dto){return service.create(dto);}

    @GetMapping("/all")
    public List<CategoriaLowDto> getAll(){ return service.getAll();}

    @GetMapping("/{id}")
    public CategoriaLowDto getCategoriaById(@PathVariable(name = "id") Integer id){return service.findCategoriaById(id);}

    @PutMapping
    public CategoriaDto update(@RequestBody CategoriaDto dto){return service.update(dto);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Integer id){service.deleteCategoriaById(id);}

    // #### Métodos API ####

    @GetMapping("/{nome}/livros")
    public List<? extends Obras> getLivrosByCategoria(@PathVariable(name = "nome") String nome){return service.getLivrosByCategoria(nome);}

    @GetMapping("/{nome}/artigos")
    public List<? extends Obras> getArtigosByCategoria(@PathVariable(name = "nome") String nome){return service.getArtigosByCategoria(nome);}

    @GetMapping("/{nome}/obras")
    public ArrayList<Object> getObrasByCategoria(@PathVariable(name = "nome") String nome){return service.getObrasByCategoria(nome);}
}
