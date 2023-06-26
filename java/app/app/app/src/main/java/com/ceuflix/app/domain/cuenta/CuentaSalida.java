package com.ceuflix.app.domain.cuenta;

import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import com.ceuflix.app.domain.entidadesjpa.Persona;
import com.ceuflix.app.domain.entidadesjpa.TipoCuenta;

public record CuentaSalida(
        Long id_cuenta,
        String username,
        String persona,
        String persona_nombre,
        String persona_apellido,
        String tipoCuenta,
        Boolean estado_cuenta,
        String img_cuenta,
        String correo,
        Boolean verificados
) {
    public CuentaSalida(Cuenta cuenta){
        this(
                cuenta.getIdCuenta(),
                cuenta.getUsername(),
                String.format("%s %s",cuenta.getPersona().getNombre(),cuenta.getPersona().getApellido()),
                cuenta.getPersona().getNombre(),
                cuenta.getPersona().getApellido(),
                cuenta.getTipoCuenta().getNombreTipocuenta(),
                cuenta.getEstadoCuenta(),
                cuenta.getImgCuenta(),
                cuenta.getCorreo(),
                cuenta.getVerificado()
        );
    }
}
