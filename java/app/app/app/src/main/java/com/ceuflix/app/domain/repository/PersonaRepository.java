package com.ceuflix.app.domain.repository;

import com.ceuflix.app.domain.entidadesjpa.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona,Long> {
}
