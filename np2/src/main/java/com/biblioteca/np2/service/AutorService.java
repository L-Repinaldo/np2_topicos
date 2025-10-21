package com.biblioteca.np2.service;

import com.biblioteca.np2.domain.dto.Autor.AutorDto;
import com.biblioteca.np2.domain.dto.Autor.AutorLowDto;
import com.biblioteca.np2.domain.model.Autor;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.AutorRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutorRepository repository;

    //##### CRUD Básico ####//

    public AutorDto create(AutorDto dto){

        if(dto.getNome().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.autor.badrequest",
                    "O nome do autor é obrigatório.");

        }
        else if (dto.getNome().length() > 100) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.autor.badrequest",
                    "Limite de caracteres do autor é 100");

        }

        var autor = MapperUtil.parseObject(dto, Autor.class);
        var autorPersist = repository.save(autor);

        return MapperUtil.parseObject(autorPersist, AutorDto.class);

    }

    public List<AutorLowDto> getAll(){

        return MapperUtil.parseListObject( repository.findAll(), AutorLowDto.class);

    }

    public AutorLowDto findAutorById(Integer id){

        var autor = repository.findById(id).orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND,
                "np2.service.autor.notfound",
                "O autor não foi encontrado"));

        return MapperUtil.parseObject(autor, AutorLowDto.class);

    }

    public AutorDto update(AutorDto dto){

        var autor = MapperUtil.parseObject(dto, Autor.class);
        var autorPersist = repository.save(autor);

        return MapperUtil.parseObject(autorPersist, AutorDto.class);

    }

    public void deleteAutorById(Integer id){

        repository.deleteById(id);

    }




    //#### Métodos Para a API ####//

    public Autor findOrCreateByName(String nome) {
        var autor = repository.findByNomeIgnoreCases(nome).orElseGet(() -> {
            AutorDto dto = new AutorDto();
            dto.setNome(nome);
            return  MapperUtil.parseObject(create(dto), Autor.class);
        });

        return MapperUtil.parseObject(autor, Autor.class);

    }




}
