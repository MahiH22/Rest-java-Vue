package com.ceuflix.app.infra.security;

import com.ceuflix.app.controller.api.CuentaController;
import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import com.ceuflix.app.domain.repository.CuentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

public class AuthenticationMangerApp implements AuthenticationManager {

    @Autowired
    private CuentaController cuentaController;
    @Autowired
    private CuentaRepository cuentaRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        String role = authentication.getAuthorities().toString();

        System.out.println(authentication);

        System.out.println(role);
        System.out.println(role);
        /*
        if (cuentaController.verificarCredenciales(username, password)) {
            Cuenta cuenta= cuentaRepository.findByUsername(username);
            System.out.println(cuenta.getTipoCuenta().getNombreTipocuenta());
            return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(new SimpleGrantedAuthority(cuenta.getTipoCuenta().getNombreTipocuenta())));
        } else {
            throw new BadCredentialsException("Las credenciales proporcionadas no son válidas");
        }
         */
        return null;
    }
}
