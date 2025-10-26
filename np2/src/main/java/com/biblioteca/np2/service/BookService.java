package com.biblioteca.np2.service;

import com.biblioteca.np2.client.GoogleBooksClient;
import com.biblioteca.np2.domain.model.book.BookDto;
import com.biblioteca.np2.domain.model.book.BookEntity;
import com.biblioteca.np2.excepiton.ApiException;
import com.biblioteca.np2.repository.BookRepository;
import com.biblioteca.np2.util.BookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private static final int DEFAULT_LIMIT = 5;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private GoogleBooksClient googleBooksClient;
    
    // ==================== CRUD DE LIVROS SALVOS ====================
    
    /**
     * Criar um novo livro no banco
     */
    public BookDto create(BookDto bookDto) {
        log.info("BookService: Criando novo livro - title: '{}'", bookDto.getTitle());
        
        if (bookDto.getTitle() == null || bookDto.getTitle().trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.book.badrequest",
                    "O título do livro não pode estar vazio");
        }
        
        BookEntity entity = BookMapper.toEntity(bookDto);
        BookEntity savedEntity = bookRepository.save(entity);
        
        log.info("BookService: Livro criado com ID: {}", savedEntity.getId());
        return BookMapper.toDto(savedEntity);
    }
    
    /**
     * Listar todos os livros salvos
     */
    public List<BookDto> findAll() {
        log.info("BookService: Listando todos os livros salvos");
        
        List<BookEntity> entities = bookRepository.findAll();
        
        log.info("BookService: {} livros encontrados", entities.size());
        return entities.stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar livro por ID
     */
    public BookDto findById(Long id) {
        log.info("BookService: Buscando livro por ID: {}", id);
        
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "np2.service.book.notfound",
                        "Livro não encontrado com ID: " + id));
        
        log.info("BookService: Livro encontrado: {}", entity.getTitle());
        return BookMapper.toDto(entity);
    }
    
    /**
     * Atualizar um livro existente
     */
    public BookDto update(Long id, BookDto bookDto) {
        log.info("BookService: Atualizando livro ID: {}", id);
        
        BookEntity existingEntity = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "np2.service.book.notfound",
                        "Livro não encontrado com ID: " + id));
        
        if (bookDto.getTitle() != null && !bookDto.getTitle().trim().isEmpty()) {
            existingEntity.setTitle(bookDto.getTitle());
        }
        if (bookDto.getAuthors() != null) {
            existingEntity.setAuthors(String.join(", ", bookDto.getAuthors()));
        }
        if (bookDto.getPublishedYear() != null) {
            existingEntity.setPublishedYear(bookDto.getPublishedYear());
        }
        if (bookDto.getInfoLink() != null) {
            existingEntity.setInfoLink(bookDto.getInfoLink());
        }
        if (bookDto.getThumbnail() != null) {
            existingEntity.setThumbnail(bookDto.getThumbnail());
        }
        if (bookDto.getFavorite() != null) {
            existingEntity.setFavorite(bookDto.getFavorite());
        }
        
        BookEntity updatedEntity = bookRepository.save(existingEntity);
        
        log.info("BookService: Livro atualizado com sucesso");
        return BookMapper.toDto(updatedEntity);
    }
    
    /**
     * Remover um livro
     */
    public void delete(Long id) {
        log.info("BookService: Removendo livro ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new ApiException(HttpStatus.NOT_FOUND,
                    "np2.service.book.notfound",
                    "Livro não encontrado com ID: " + id);
        }
        
        bookRepository.deleteById(id);
        
        log.info("BookService: Livro removido com sucesso");
    }
    
    /**
     * Listar livros favoritos
     */
    public List<BookDto> findFavorites() {
        log.info("BookService: Listando livros favoritos");
        
        List<BookEntity> favorites = bookRepository.findByFavoriteTrue();
        
        log.info("BookService: {} livros favoritos encontrados", favorites.size());
        return favorites.stream()
                .map(BookMapper::toDto)
                .collect(Collectors.toList());
    }
    
    // ==================== BUSCA GOOGLE BOOKS API ====================
    
    /**
     * Buscar livros na Google Books API
     */
    public List<BookDto> searchGoogleBooks(String query, Integer limit) {
        log.info("BookService: Buscando livros na Google Books API - query: '{}', limit: {}", query, limit);
        
        if (query == null || query.trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "np2.service.book.badrequest",
                    "O termo de busca não pode estar vazio");
        }
        
        int finalLimit = (limit == null || limit < 1) ? DEFAULT_LIMIT : limit;
        
        try {
            List<BookDto> books = googleBooksClient.searchBooks(query.trim(), finalLimit);
            
            log.info("BookService: {} livros encontrados na Google Books API", books.size());
            return books;
            
        } catch (Exception e) {
            log.error("BookService: Erro ao buscar na Google Books API: {}", e.getMessage(), e);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "np2.service.book.error",
                    "Erro ao consultar Google Books API");
        }
    }
}


