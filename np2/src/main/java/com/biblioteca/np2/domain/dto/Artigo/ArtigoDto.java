package com.biblioteca.np2.domain.dto.Artigo;


import lombok.Data;

import java.util.List;

@Data
public class ArtigoDto {
    private Integer id;

    private List<String> autores;

    private String titulo;
    private String categoria;
    private String editora;
}
