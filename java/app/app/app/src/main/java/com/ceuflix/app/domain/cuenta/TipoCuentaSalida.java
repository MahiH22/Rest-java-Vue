package com.ceuflix.app.domain.cuenta;

import com.ceuflix.app.domain.entidadesjpa.TipoCuenta;

public record TipoCuentaSalida(
        Long id_tipo_cuenta,
        String nombre_tipo_cuenta,
        String acceso_de_cuenta
) {
    public TipoCuentaSalida(TipoCuenta tipoCuenta){
        this(
                tipoCuenta.getIdTipoCuenta(),
                tipoCuenta.getNombreTipocuenta(),
                tipoCuenta.getAccesoDeCuenta()
        );
    }
}
