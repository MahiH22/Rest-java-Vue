package com.ceuflix.app.domain.pelicula;

import com.ceuflix.app.domain.entidadesjpa.Genero;
import com.ceuflix.app.domain.entidadesjpa.Pelicula;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public record PeliculaSalida(
        Long id_pelicula,
        String nombre,
        String link_portada,
        String link_pelicula,
        int rating,
        String descripcion,
        int duracion,
        Boolean estado_pelicula,
        Boolean pelicula_adulto,
        List<String> generos
) {
    public PeliculaSalida(Pelicula pelicula) {
        this(
                pelicula.getIdPelicula(),
                pelicula.getNombrePelicula(),
                pelicula.getLinkPortada(),
                pelicula.getLinkPelicula(),
                pelicula.getRating(),
                pelicula.getDescripcion(),
                pelicula.getDuracion(),
                pelicula.getEstadoPelicula(),
                pelicula.getPeliculaAdulto(),
                obtenerNombresGeneros(pelicula.getGeneros())
        );
    }
    private static List<String> obtenerNombresGeneros(List<Genero> generos) {
        if (generos == null) {
            return Collections.emptyList(); // O cualquier otro valor predeterminado apropiado
        }

        return generos.stream()
                .map(Genero::getNombreGenero)
                .collect(Collectors.toList());
    }
}

