package com.ceuflix.app.domain.pelicula;

public record GeneroEntrada(
        Long id_genero,
        String nombre_genero,
        String descripcion_genero,
        Boolean estado_genero
) {
}
