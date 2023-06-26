package com.ceuflix.app.domain.cuenta;

import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import com.ceuflix.app.domain.entidadesjpa.Perfil;

public record PerfilSalida(
        Long id_perfil,
        String nombre_perfil,
        Boolean edad_perfil
) {
    public PerfilSalida(Perfil perfil){
        this(
                perfil.getIdPerfil(),
                perfil.getNombrePerfil(),
                perfil.getEdadPerfil()
        );
    }
}
