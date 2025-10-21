package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Integer> {

    //@Query é usada no Spring Data JPA para definir consultas personalizadas em bancos de dados
    @Query("SELECT autor FROM Autor autor WHERE LOWER(autor.nome) = LOWER(:nome)")
    Optional<Autor> findByNomeIgnoreCases(String nome);


}
