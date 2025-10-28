package com.biblioteca.np2.domain.dto.Livro;

import com.biblioteca.np2.domain.dto.Obras;
import lombok.Data;

@Data
public class LivroLowDto extends Obras {

    private String titulo;

    private  String nomeAutor;
    private String nomeCategoria;
    private String nomeEditora;
}
