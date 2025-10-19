package br.edu.unichristus.backend.domain.model.book;

import lombok.Data;
import java.util.List;

@Data
public class BookDTO {
    
    private String title;
    private List<String> authors;
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
    
}
