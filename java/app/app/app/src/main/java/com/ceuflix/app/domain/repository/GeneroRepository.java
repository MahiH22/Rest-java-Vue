package com.ceuflix.app.domain.repository;

import com.ceuflix.app.domain.entidadesjpa.Genero;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero,Long> {
    Genero findByNombreGenero(String s);

    Page<Genero> findByEstadoGeneroTrue(Pageable paginacion);
}
