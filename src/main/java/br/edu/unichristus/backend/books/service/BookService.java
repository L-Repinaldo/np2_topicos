package br.edu.unichristus.backend.books.service;

import br.edu.unichristus.backend.books.dto.BookDTO;
import br.edu.unichristus.backend.books.entity.BookEntity;
import br.edu.unichristus.backend.books.mapper.BookMapper;
import br.edu.unichristus.backend.books.repository.BookRepository;
import br.edu.unichristus.backend.client.GoogleBooksClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    /**
     * Criar um novo livro
     */
    public BookDTO create(BookDTO bookDTO) {
        log.info("BookService: Criando novo livro - title: '{}'", bookDTO.getTitle());
        
        if (bookDTO.getTitle() == null || bookDTO.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("O título do livro não pode estar vazio");
        }
        
        BookEntity entity = BookMapper.toEntity(bookDTO);
        BookEntity savedEntity = bookRepository.save(entity);
        
        log.info("BookService: Livro criado com ID: {}", savedEntity.getId());
        return BookMapper.toDTO(savedEntity);
    }
    
    /**
     * Listar todos os livros
     */
    public List<BookDTO> findAll() {
        log.info("BookService: Listando todos os livros");
        
        List<BookEntity> entities = bookRepository.findAll();
        
        log.info("BookService: {} livros encontrados", entities.size());
        return entities.stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Buscar livro por ID
     */
    public BookDTO findById(Long id) {
        log.info("BookService: Buscando livro por ID: {}", id);
        
        BookEntity entity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));
        
        log.info("BookService: Livro encontrado: {}", entity.getTitle());
        return BookMapper.toDTO(entity);
    }
    
    /**
     * Atualizar um livro existente
     */
    public BookDTO update(Long id, BookDTO bookDTO) {
        log.info("BookService: Atualizando livro ID: {}", id);
        
        BookEntity existingEntity = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com ID: " + id));
        
        if (bookDTO.getTitle() != null && !bookDTO.getTitle().trim().isEmpty()) {
            existingEntity.setTitle(bookDTO.getTitle());
        }
        if (bookDTO.getAuthors() != null) {
            existingEntity.setAuthors(String.join(", ", bookDTO.getAuthors()));
        }
        if (bookDTO.getPublishedYear() != null) {
            existingEntity.setPublishedYear(bookDTO.getPublishedYear());
        }
        if (bookDTO.getInfoLink() != null) {
            existingEntity.setInfoLink(bookDTO.getInfoLink());
        }
        if (bookDTO.getThumbnail() != null) {
            existingEntity.setThumbnail(bookDTO.getThumbnail());
        }
        if (bookDTO.getFavorite() != null) {
            existingEntity.setFavorite(bookDTO.getFavorite());
        }
        
        BookEntity updatedEntity = bookRepository.save(existingEntity);
        
        log.info("BookService: Livro atualizado com sucesso");
        return BookMapper.toDTO(updatedEntity);
    }
    
    /**
     * Remover um livro
     */
    public void delete(Long id) {
        log.info("BookService: Removendo livro ID: {}", id);
        
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado com ID: " + id);
        }
        
        bookRepository.deleteById(id);
        
        log.info("BookService: Livro removido com sucesso");
    }
    
    /**
     * Buscar livros na Google Books API
     */
    public List<BookDTO> searchGoogleBooks(String query, Integer limit) {
        log.info("BookService: Buscando livros na Google Books API - query: '{}', limit: {}", query, limit);
        
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("O termo de busca não pode estar vazio");
        }
        
        int finalLimit = (limit == null || limit < 1) ? DEFAULT_LIMIT : limit;
        
        try {
            List<BookDTO> books = googleBooksClient.searchBooks(query.trim(), finalLimit);
            
            log.info("BookService: {} livros encontrados na Google Books API", books.size());
            return books;
            
        } catch (Exception e) {
            log.error("BookService: Erro ao buscar na Google Books API: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao consultar Google Books API", e);
        }
    }
}

