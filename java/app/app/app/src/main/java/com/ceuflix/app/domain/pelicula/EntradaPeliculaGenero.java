package com.ceuflix.app.domain.pelicula;

import jakarta.validation.constraints.NotNull;

public record EntradaPeliculaGenero(
        @NotNull String nombre_pelicula,
        @NotNull String nombre_genero
) {
}
