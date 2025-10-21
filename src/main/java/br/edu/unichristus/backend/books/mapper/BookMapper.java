package br.edu.unichristus.backend.books.mapper;

import br.edu.unichristus.backend.books.dto.BookDTO;
import br.edu.unichristus.backend.books.entity.BookEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    
    /**
     * Converte BookEntity para BookDTO
     */
    public static BookDTO toDTO(BookEntity entity) {
        if (entity == null) {
            return null;
        }
        
        BookDTO dto = new BookDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setAuthors(parseAuthorsToList(entity.getAuthors()));
        dto.setPublishedYear(entity.getPublishedYear());
        dto.setInfoLink(entity.getInfoLink());
        dto.setThumbnail(entity.getThumbnail());
        dto.setFavorite(entity.getFavorite());
        
        return dto;
    }
    
    /**
     * Converte BookDTO para BookEntity
     */
    public static BookEntity toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        
        BookEntity entity = new BookEntity();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setAuthors(parseAuthorsToString(dto.getAuthors()));
        entity.setPublishedYear(dto.getPublishedYear());
        entity.setInfoLink(dto.getInfoLink());
        entity.setThumbnail(dto.getThumbnail());
        entity.setFavorite(dto.getFavorite() != null ? dto.getFavorite() : false);
        
        return entity;
    }
    
    /**
     * Converte string de autores separada por vírgula para lista
     */
    private static List<String> parseAuthorsToList(String authors) {
        if (authors == null || authors.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.stream(authors.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
    
    /**
     * Converte lista de autores para string separada por vírgula
     */
    private static String parseAuthorsToString(List<String> authors) {
        if (authors == null || authors.isEmpty()) {
            return "";
        }
        return String.join(", ", authors);
    }
}

