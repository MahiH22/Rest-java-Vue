package com.ceuflix.app.domain.entidadesjpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idTipoCuenta")
@Entity
@Table(name="tipo_cuenta")
public class TipoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_cuenta")
    private Long idTipoCuenta;

    @Column(name = "nombre_tipo_cuenta")
    private String nombreTipocuenta;

    @Column(name = "acceso_de_cuenta")
    private String accesoDeCuenta;


    public String getNombre() {
        return this.nombreTipocuenta;
    }
}
