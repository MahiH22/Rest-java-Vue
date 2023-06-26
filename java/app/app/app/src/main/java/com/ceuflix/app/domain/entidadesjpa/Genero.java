package com.ceuflix.app.domain.entidadesjpa;

import com.ceuflix.app.domain.pelicula.GeneroEntrada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idGenero")
@Entity
@Table(name="genero_pelicula")
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id_genero")
    private Long idGenero;

    @Column(name = "nombre_genero")
    private String nombreGenero;

    @Column(name = "descripcion_genero")
    private String descripcionGenero;

    @Column(name = "estado_genero")
    private Boolean estadoGenero;


    public Genero(GeneroEntrada generoEntrada) {
        this.nombreGenero=generoEntrada.nombre_genero();
        this.descripcionGenero=generoEntrada.descripcion_genero();
        this.estadoGenero=true;
    }

    public void actualizarDatos(GeneroEntrada generoEntrada) {
        if(generoEntrada.nombre_genero() != null){
            this.nombreGenero=generoEntrada.nombre_genero();
        }
        if (generoEntrada.descripcion_genero() != null){
            this.descripcionGenero=generoEntrada.descripcion_genero();
        }
        if(generoEntrada.estado_genero() != null){
            this.estadoGenero=generoEntrada.estado_genero();
        }
    }

    public void desactivarGenero() {
        this.estadoGenero=false;
    }
}
