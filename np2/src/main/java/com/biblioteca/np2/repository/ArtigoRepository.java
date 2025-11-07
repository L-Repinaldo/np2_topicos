package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Artigo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArtigoRepository extends JpaRepository<Artigo, Integer> {

    @Query("SELECT artigos FROM Artigo artigos WHERE LOWER(artigos.editora.nome) = (:nome)")
    List<Artigo> getArtigosByEditora(String nome);

    @Query("SELECT artigos FROM Artigo artigos WHERE LOWER(artigos.categoria.nome) = (:nome)")
    List<Artigo> getArtigosByCategoria(String nome);

}