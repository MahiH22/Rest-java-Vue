package com.ceuflix.app.domain.pelicula;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PeliculaEntradaPostGet(
         String nombre,
         String link_pelicula,
         String link_imagen,
         int rating,
         String descripcion,
         int duracion,
         Boolean estado_pelicula,
         Boolean pelicula_adulto
) {
}
