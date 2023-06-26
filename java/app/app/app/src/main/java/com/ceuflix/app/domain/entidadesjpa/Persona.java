package com.ceuflix.app.domain.entidadesjpa;

import com.ceuflix.app.domain.cuenta.CuentaEntrada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "Id")
@Entity
@Table(name="persona")
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    public Persona(CuentaEntrada cuentaEntrada) {
        this.nombre=cuentaEntrada.persona().nombre();
        this.apellido=cuentaEntrada.persona().apellido();
    }

    public void actualizarDatos(CuentaEntrada cuentaEntrada) {
        if(cuentaEntrada.persona().nombre() != null){
            this.nombre=cuentaEntrada.persona().nombre();
        }
        if(cuentaEntrada.persona().apellido() != null){
            this.apellido=cuentaEntrada.persona().apellido();
        }
    }
}
