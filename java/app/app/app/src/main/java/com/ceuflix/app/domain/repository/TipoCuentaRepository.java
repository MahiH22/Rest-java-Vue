package com.ceuflix.app.domain.repository;

import com.ceuflix.app.domain.entidadesjpa.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCuentaRepository extends JpaRepository<TipoCuenta,Long> {
}
