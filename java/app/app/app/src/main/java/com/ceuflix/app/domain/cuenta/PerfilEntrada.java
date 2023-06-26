package com.ceuflix.app.domain.cuenta;

public record PerfilEntrada(
        Long id_perfil,
        Long id_cuenta,
        String nombre_perfil,
        Boolean edad_perfil,
        Boolean estado_perfil
) {
}
