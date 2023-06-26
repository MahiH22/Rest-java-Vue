package com.ceuflix.app.domain.pelicula;

import com.ceuflix.app.domain.entidadesjpa.Genero;

public record GeneroSalida(
        String nombre_genero,
        String descipcion_genero_
) {
    public GeneroSalida (Genero genero){
        this(
                genero.getNombreGenero(),
                genero.getDescripcionGenero()
        );
    }
}
