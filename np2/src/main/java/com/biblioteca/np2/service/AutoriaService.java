package com.biblioteca.np2.service;


import com.biblioteca.np2.domain.model.Artigo;
import com.biblioteca.np2.domain.model.Autor;
import com.biblioteca.np2.domain.model.Autoria;
import com.biblioteca.np2.domain.model.AutoriaId;
import com.biblioteca.np2.repository.AutoriaRepository;
import com.biblioteca.np2.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoriaService {

    @Autowired
    private AutoriaRepository repository;

    public void createVinculo(Artigo artigo, Autor autor ){
        AutoriaId id = new AutoriaId(artigo.getId(), autor.getId());

        if(!repository.existsById(id)){
            repository.save(new Autoria(id, artigo, autor));
        }
    }

    public List<Artigo> getArtigosByAutor(String nome_autor){

        return MapperUtil.parseListObject(repository.getArtigosByAutor(nome_autor), Artigo.class);
    }
}
