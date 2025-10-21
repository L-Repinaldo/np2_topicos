package br.edu.unichristus.backend.books.repository;

import br.edu.unichristus.backend.books.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
}

