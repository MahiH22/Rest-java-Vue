package com.ceuflix.app.domain.pelicula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PeliculaEntradaPut(
        @NotNull Long id,
        String nombre,
        String link_pelicula,
        String link_portada,
        Integer rating,
        String descripcion,
        Integer duracion,
        Boolean estado_pelicula,
        Boolean pelicula_adulto
) {
}
