package com.biblioteca.np2.service;


import com.biblioteca.np2.domain.dto.Categoria.CategoriaDto;
import com.biblioteca.np2.domain.dto.Categoria.CategoriaLowDto;
import com.biblioteca.np2.domain.dto.Obras;
import com.biblioteca.np2.domain.model.Categoria;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.CategoriaRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private ArtigoService artigoService;

    //#### CRUD Básico ####

    public CategoriaDto create(CategoriaDto dto){

        if(dto.getNome().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.categoria.badrequest",
                    "O nome da categoria é obrigatório.");

        }
        else if (dto.getNome().length() > 100) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.categoria.badrequest",
                    "Limite de caracteres do nome é 100");

        }

        var categoria = MapperUtil.parseObject(dto, Categoria.class);
        var categoriaPersist = repository.save(categoria);

        return MapperUtil.parseObject(categoriaPersist, CategoriaDto.class);

    }

    public List<CategoriaLowDto> getAll(){

        return MapperUtil.parseListObject( repository.findAll(), CategoriaLowDto.class);

    }

    public CategoriaLowDto findCategoriaById(Integer id){

        var categoria = repository.findById(id).orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND,
                "np2.service.categoria.notfound",
                "A categoria não foi encontrada"));

        return MapperUtil.parseObject(categoria, CategoriaLowDto.class);

    }

    public CategoriaDto update(CategoriaDto dto){

        var categoria = MapperUtil.parseObject(dto, Categoria.class);
        var categoriaPersist = repository.save(categoria);

        return MapperUtil.parseObject(categoriaPersist, CategoriaDto.class);

    }

    public void deleteCategoriaById(Integer id){

        repository.deleteById(id);

    }

    //#### Métodos API ####

    public Categoria findOrCreateByName(String nome) {
        var categoria =  repository.findByNomeIgnoreCases(nome).orElseGet(() -> {
            CategoriaDto dto = new CategoriaDto();
            dto.setNome(nome);
            return MapperUtil.parseObject(create(dto), Categoria.class);
        });

        return MapperUtil.parseObject(categoria, Categoria.class);
    }

    public List<? extends Obras> getLivrosByCategoria(String categoria){
        return livroService.findLivrosByCategoria(categoria);
    }

    public List<? extends Obras> getArtigosByCategoria(String categoria){
        return artigoService.findArtigosByCategoria(categoria);
    }

    public ArrayList<Object> getObrasByCategoria(String categoria){

        var obras = new ArrayList<>();

        obras.add(getLivrosByCategoria(categoria));
        obras.add(getArtigosByCategoria(categoria));

        return obras;
    }

}
