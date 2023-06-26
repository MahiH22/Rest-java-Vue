package com.ceuflix.app.domain.repository;

import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.DoubleStream;

public interface CuentaRepository extends JpaRepository<Cuenta,Long> {
    Page<Cuenta> findByEstadoCuentaTrueAndVerificadoTrue(Pageable paginacion);

    UserDetails findByUsername(String username);

    Page<Cuenta> findByEstadoCuentaTrue(Pageable paginacion);

    //Cuenta findByUsername(String username);


}
