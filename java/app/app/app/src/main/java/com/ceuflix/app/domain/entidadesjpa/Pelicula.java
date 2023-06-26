package com.ceuflix.app.domain.entidadesjpa;

import com.ceuflix.app.domain.pelicula.PeliculaEntradaPostGet;
import com.ceuflix.app.domain.pelicula.PeliculaEntradaPut;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/*
@OneToOne
@PrimaryKeyJoinColumn
private Persona persona;
 */

/*
@ManyToOne
@Column(name = "id_persona")
private Persona persona;
 */

/*
@OneToMany(mappedBy = "persona")
private List<Direccion> direcciones;
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@EqualsAndHashCode(of = "Id") por si aglo no funciona en pelicula
@EqualsAndHashCode(of = "idPelicula")
@Entity
@Table(name="pelicula")
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula")
    private Long idPelicula;

    @Column(name = "nombre")
    private String nombrePelicula;

    @Column(name = "link_pelicula")
    private String linkPelicula;

    @Column(name = "link_portada")
    private String linkPortada;

    @Column(name = "rating")
    private int rating;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "duracion")
    private int duracion;

    @Column(name = "estado_pelicula")
    private Boolean estadoPelicula;

    @Column(name = "pelicula_adulto")
    private Boolean peliculaAdulto;

    @ManyToMany
    @JoinTable(
            name = "generos_de_pelicula",
            joinColumns = @JoinColumn(name = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_genero")
    )
    private List<Genero> generos;

    public Pelicula(PeliculaEntradaPostGet peli) {
        this.nombrePelicula=peli.nombre();
        this.linkPelicula=peli.link_pelicula();
        this.linkPortada=peli.link_imagen();
        this.rating=peli.rating();
        this.descripcion=peli.descripcion();
        this.duracion=peli.duracion();
        this.estadoPelicula=peli.estado_pelicula();
        this.peliculaAdulto=peli.pelicula_adulto();
    }

    public Pelicula actualizarDatos(PeliculaEntradaPut peli) {
        if(peli.nombre() != null){
            this.nombrePelicula=peli.nombre();
        }
        if(peli.link_pelicula() != null){
            this.linkPelicula=peli.link_pelicula();
        }
        if(peli.descripcion() != null){
            this.descripcion=peli.descripcion();
        }
        if(peli.duracion() != null){
            this.duracion=peli.duracion();
        }
        if(peli.rating() != null){
            this.duracion=peli.duracion();
        }
        if(peli.estado_pelicula() != null){
            this.estadoPelicula=peli.estado_pelicula();
        }
        if(peli.pelicula_adulto() != null){
            this.peliculaAdulto=peli.pelicula_adulto();
        }
        if(peli.link_portada() != null){
            this.linkPortada=peli.link_portada();
        }
        return this;
    }
    public void desactivarPelicula() {
        estadoPelicula=false;
    }

    public void agregarGenero(Genero genero){
        this.generos.add(genero);
    }
    public void eliminarGenero(Genero genero){
        this.generos.remove(genero);
    }
}
