package com.ceuflix.app.domain.entidadesjpa;

import com.ceuflix.app.domain.cuenta.PerfilEntrada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idPerfil")
@Entity
@Table(name = "perfil")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_perfil")
    private Long idPerfil;

    @Column(name = "nombre_perfil")
    private String nombrePerfil;

    @Column(name = "edad_pefil")
    private Boolean edadPerfil;

    @Column(name = "Estado_Perfil")
    private Boolean estadoPerfil;

    @ManyToOne
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    public Perfil(PerfilEntrada perfilEntrada,Cuenta cuenta) {
        this.nombrePerfil= perfilEntrada.nombre_perfil();
        this.edadPerfil=perfilEntrada.edad_perfil();
        this.estadoPerfil= true;
        this.cuenta=cuenta;
    }

    public void desactiva() {
        this.estadoPerfil=false;
    }

    public void actualizar(PerfilEntrada perfilEntrada) {
        if(perfilEntrada.nombre_perfil() != null){
            this.nombrePerfil=perfilEntrada.nombre_perfil();
        }
        if(perfilEntrada.edad_perfil() != null){
            this.edadPerfil=perfilEntrada.edad_perfil();
        }
        if(perfilEntrada.estado_perfil() != null){
            this.estadoPerfil=perfilEntrada.estado_perfil();
        }
    }
}
