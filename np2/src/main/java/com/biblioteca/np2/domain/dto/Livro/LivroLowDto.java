package com.biblioteca.np2.domain.dto.Livro;


import lombok.Data;

@Data
public class LivroLowDto {

    private String titulo;

    private  String nomeAutor;
    private String nomeCategoria;
    private String nomeEditora;
}
