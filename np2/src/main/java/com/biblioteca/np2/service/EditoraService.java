package com.biblioteca.np2.service;


import com.biblioteca.np2.domain.dto.Editora.EditoraDto;
import com.biblioteca.np2.domain.dto.Editora.EditoraLowDto;
import com.biblioteca.np2.domain.model.Editora;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.EditoraRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EditoraService {

    @Autowired
    private EditoraRepository repository;

    //#### CRUD Básico ####

    public EditoraDto create(EditoraDto dto){

        if(dto.getNome().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.editora.badrequest",
                    "O nome da editora é obrigatório.");

        }
        else if (dto.getNome().length() > 100) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.editora.badrequest",
                    "Limite de caracteres do nome é 100");

        }

        var editora = MapperUtil.parseObject(dto, Editora.class);
        var editoraPersist = repository.save(editora);

        return MapperUtil.parseObject(editoraPersist, EditoraDto.class);

    }

    public List<EditoraLowDto> getAll(){

        return MapperUtil.parseListObject( repository.findAll(), EditoraLowDto.class);

    }

    public EditoraLowDto findEditoraById(Integer id){

        var editora = repository.findById(id).orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND,
                "np2.service.editora.notfound",
                "A editora não foi encontrada"));

        return MapperUtil.parseObject(editora, EditoraLowDto.class);

    }

    public EditoraDto update(EditoraDto dto){

        var editora = MapperUtil.parseObject(dto, Editora.class);
        var editoraPersist = repository.save(editora);

        return MapperUtil.parseObject(editoraPersist, EditoraDto.class);

    }

    public void deleteEditoraById(Integer id){

        repository.deleteById(id);

    }

    //#### Métodos API ####

    public Editora findOrCreateByName(String nome){
        var editora =   repository.findByNomeIgnoreCases(nome).orElseGet(() -> {
            EditoraDto dto = new EditoraDto();
            dto.setNome(nome);
            return MapperUtil.parseObject(create(dto), Editora.class);
        });

        return MapperUtil.parseObject(editora, Editora.class);
    }


}
