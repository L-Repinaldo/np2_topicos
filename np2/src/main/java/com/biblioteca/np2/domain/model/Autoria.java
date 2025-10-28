package com.biblioteca.np2.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_autoria_artigos")
public class Autoria {

    @EmbeddedId
    private AutoriaId id;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_artigo")
    @JoinColumn(name = "id_artigo", nullable = false)
    private Artigo artigo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("id_autor")
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;

}
