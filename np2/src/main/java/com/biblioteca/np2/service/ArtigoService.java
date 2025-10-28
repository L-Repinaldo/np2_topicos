package com.biblioteca.np2.service;


import com.biblioteca.np2.domain.dto.Artigo.ArtigoDto;
import com.biblioteca.np2.domain.dto.Artigo.ArtigoLowDto;
import com.biblioteca.np2.domain.model.Artigo;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.ArtigoRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtigoService {

    @Autowired
    private ArtigoRepository repository;

    @Autowired
    private AutoriaService autoriaService;

    @Autowired
    @Lazy
    private AutorService autorService;

    @Autowired
    @Lazy
    private CategoriaService categoriaService;

    @Autowired
    @Lazy
    private EditoraService editoraService;

    //#### CRUD Básico####

    public ArtigoDto create(ArtigoDto dto){

        if(dto.getTitulo().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.artigo.badrequest",
                    "O título do artigo é obrigatório.");

        }
        else if (dto.getTitulo().length() > 150) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.artigo.badrequest",
                    "Limite de caracteres do título é 150");

        }

        var categoria = categoriaService.findOrCreateByName(dto.getCategoria());
        var editora = editoraService.findOrCreateByName(dto.getEditora());

        var artigo = MapperUtil.parseObject(dto, Artigo.class);

        artigo.setCategoria(categoria);
        artigo.setEditora(editora);

        var artigoPersit = repository.save(artigo);


        //Preencher relação Artigo x Autor
        for(String nomeAutor : dto.getAutores()){

            var autor = autorService.findOrCreateByName(nomeAutor);
            autoriaService.createVinculo(artigoPersit, autor);

        }

        artigoPersit.setAutores(dto.getAutores());


        return MapperUtil.parseObject(artigoPersit, ArtigoDto.class);

    }

    public List<ArtigoLowDto> getAll(){

        return MapperUtil.parseListObject( repository.findAll(), ArtigoLowDto.class);

    }

    public ArtigoLowDto findLivroById(Integer id){

        var artigo = repository.findById(id).orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND,
                "np2.service.artigo.notfound",
                "O artigo não foi encontrado"));

        return MapperUtil.parseObject(artigo, ArtigoLowDto.class);

    }

    public ArtigoDto update(ArtigoDto dto){

        var artigo = MapperUtil.parseObject(dto, Artigo.class);
        var artigoPersist = repository.save(artigo);

        return MapperUtil.parseObject(artigoPersist, ArtigoDto.class);

    }

    public void deleteArtigoById(Integer id){

        repository.deleteById(id);

    }

    public List<ArtigoLowDto> findArtigosByAutor(String nome_autor){

        return MapperUtil.parseListObject(autoriaService.getArtigosByAutor(nome_autor.toLowerCase()), ArtigoLowDto.class);
    }

    public List<ArtigoLowDto> findArtigosByCategoria(String nome_categoria){

        return MapperUtil.parseListObject(repository.getArtigosByCategoria(nome_categoria.toLowerCase()), ArtigoLowDto.class);
    }

    public List<ArtigoLowDto> findArtigosByEditora(String nome_editora){

        return MapperUtil.parseListObject(repository.getArtigosByEditora(nome_editora.toLowerCase()), ArtigoLowDto.class);
    }


}
