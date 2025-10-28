package com.biblioteca.np2.service;


import com.biblioteca.np2.domain.dto.Livro.LivroDto;
import com.biblioteca.np2.domain.dto.Livro.LivroLowDto;
import com.biblioteca.np2.domain.model.Livro;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.LivroRepository;
import com.biblioteca.np2.util.MapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

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

    public LivroDto create(LivroDto dto){

        if(dto.getTitulo().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.livro.badrequest",
                    "O título do livro é obrigatório.");

        }
        else if (dto.getTitulo().length() > 150) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.livro.badrequest",
                    "Limite de caracteres do título é 150");

        }

        if(dto.getIsbn().isBlank()){

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.livro.badrequest",
                    "O isbn do livro é obrigatório.");

        }
        else if (dto.getIsbn().length() != 13) {

            throw new ApiException(HttpStatus.BAD_REQUEST, "np2.service.livro.badrequest",
                    "O isbn deve ter 13 caracteres");

        }

        var autor = autorService.findOrCreateByName(dto.getAutor());
        var categoria = categoriaService.findOrCreateByName(dto.getCategoria());
        var editora = editoraService.findOrCreateByName(dto.getEditora());

        
        var livro = MapperUtil.parseObject(dto, Livro.class);
        livro.setAutor(autor);

        livro.setCategoria(categoria);
        livro.setEditora(editora);
        var livroPersist = repository.save(livro);

        return MapperUtil.parseObject(livroPersist, LivroDto.class);

    }

    public List<LivroLowDto> getAll(){

        return MapperUtil.parseListObject( repository.findAll(), LivroLowDto.class);

    }

    public LivroLowDto findLivroById(Integer id){

        var livro = repository.findById(id).orElseThrow( () -> new ApiException(HttpStatus.NOT_FOUND,
                "np2.service.livro.notfound",
                "O livro não foi encontrado"));

        return MapperUtil.parseObject(livro, LivroLowDto.class);

    }

    public LivroDto update(LivroDto dto){

        var livro = MapperUtil.parseObject(dto, Livro.class);
        var livroPersist = repository.save(livro);

        return MapperUtil.parseObject(livroPersist, LivroDto.class);

    }

    public void deleteLivroById(Integer id){

        repository.deleteById(id);

    }

    public List<LivroLowDto> findLivrosByAutor(String nome_autor){

        return MapperUtil.parseListObject(repository.getLivrosByAutor(nome_autor.toLowerCase()), LivroLowDto.class);
    }

    public List<LivroLowDto> findLivrosByCategoria(String nome_categoria){

        return MapperUtil.parseListObject(repository.getLivrosByCategoria(nome_categoria.toLowerCase()), LivroLowDto.class);
    }

    public List<LivroLowDto> findLivrosByEditora(String nome_editora){

        return MapperUtil.parseListObject(repository.getLivrosByEditora(nome_editora.toLowerCase()), LivroLowDto.class);
    }

}
