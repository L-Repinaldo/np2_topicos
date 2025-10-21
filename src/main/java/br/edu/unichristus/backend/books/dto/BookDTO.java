package br.edu.unichristus.backend.books.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private List<String> authors;
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
    private Boolean favorite;
}

