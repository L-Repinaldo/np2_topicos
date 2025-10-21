package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Integer> {}
