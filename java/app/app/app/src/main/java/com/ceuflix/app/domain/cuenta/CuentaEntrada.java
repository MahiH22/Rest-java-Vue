package com.ceuflix.app.domain.cuenta;

import com.ceuflix.app.domain.entidadesjpa.Persona;

public record CuentaEntrada(
        Long id_cuenta,
        Boolean estado_cuenta,
        String img_cuenta,
        String correo,
        String username,
        String hash_contrasenia,
        Boolean verificado,
        Long tipo_cuenta,
        PersonasEntrada persona
) {
}
