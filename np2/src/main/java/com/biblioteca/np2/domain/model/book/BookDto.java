package com.biblioteca.np2.domain.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private Long id;
    private String title;
    private List<String> authors;
    private Integer publishedYear;
    private String infoLink;
    private String thumbnail;
    private Boolean favorite;
}


