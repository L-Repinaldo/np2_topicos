package com.biblioteca.np2.repository;

import com.biblioteca.np2.domain.model.book.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    
    List<BookEntity> findByFavoriteTrue();
    
    List<BookEntity> findByTitleContainingIgnoreCase(String title);
}

