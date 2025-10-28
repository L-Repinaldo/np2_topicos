package com.biblioteca.np2.domain.dto.Artigo;

import com.biblioteca.np2.domain.dto.Obras;
import lombok.Data;

import java.util.List;

@Data
public class ArtigoLowDto extends Obras {

    private String titulo;

    private List<String> autores;

    private String categoria;
    private String editora;
}
