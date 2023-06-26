package com.ceuflix.app.controller.api;

import com.ceuflix.app.domain.cuenta.LoginEntrada;
import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import com.ceuflix.app.infra.security.DatosJWTToken;
import com.ceuflix.app.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class autenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody LoginEntrada loginEntrada){
        Authentication authToken= new UsernamePasswordAuthenticationToken(loginEntrada.username(),
                loginEntrada.hash_contrasenia());
       try{
           var usuarioAutenticado = authenticationManager.authenticate(authToken);

           if(!usuarioVerificado((Cuenta) usuarioAutenticado.getPrincipal())){
               return ResponseEntity.badRequest().body("Usuario aun no verificado");
           }

           var JWTtoken =tokenService.generarToken((Cuenta) usuarioAutenticado.getPrincipal());
           if (JWTtoken == null){
               return ResponseEntity.badRequest().body("Revisar credenciales");
           }

           return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
       }catch (AuthenticationException e){
           return ResponseEntity.badRequest().body("Revisar Credenciales");
       }
    }

    private Boolean usuarioVerificado(Cuenta cuenta){
        return cuenta.getVerificado();
    }

}
