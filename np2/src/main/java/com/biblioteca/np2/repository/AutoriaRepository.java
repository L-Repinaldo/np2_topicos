package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Artigo;
import com.biblioteca.np2.domain.model.Autoria;
import com.biblioteca.np2.domain.model.AutoriaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutoriaRepository extends JpaRepository<Autoria, AutoriaId> {

    @Query("SELECT artigo FROM Autoria autoria WHERE LOWER(autoria.autor.nome) = (:nome)")
    List<Artigo> getArtigosByAutor(String nome);
}
