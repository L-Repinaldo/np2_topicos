package com.biblioteca.np2.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AutoriaId implements Serializable {

    @Column(name = "id_artigo")
    private Integer id_artigo;

    @Column(name = "id_autor")
    private Integer id_autor;
}
