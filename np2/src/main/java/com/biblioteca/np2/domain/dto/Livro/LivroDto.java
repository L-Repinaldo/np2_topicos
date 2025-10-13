package com.biblioteca.np2.domain.dto.Livro;


import lombok.Data;

@Data
public class LivroDto {

    private Integer id;

    private String titulo;
    private String isbn;

    private  String autor;
    private String categoria;
    private String editora;



}
