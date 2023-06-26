package com.ceuflix.app.domain.repository;


import com.ceuflix.app.domain.entidadesjpa.Pelicula;
import com.ceuflix.app.domain.pelicula.PeliculaSalida;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeliculaRepository extends JpaRepository<Pelicula,Long> {

    @Cacheable("peliculas")
    Page<Pelicula> findByEstadoPeliculaTrue(Pageable paginacion);

    Pelicula findByNombrePelicula(String s);

    Page<Pelicula> findByEstadoPeliculaTrueAndNombrePeliculaContaining(Pageable paginacion, String nombre);
}
/*
query avanzada
@Query(value = "SELECT * FROM pelicula WHERE ...", nativeQuery = true)
List<Pelicula> findByCustomQuery();
 */