package com.biblioteca.np2.domain.model.book;

import lombok.Data;
import java.util.List;

@Data
public class BookDto {
    private String title;
    private List<String> authors;
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
}


