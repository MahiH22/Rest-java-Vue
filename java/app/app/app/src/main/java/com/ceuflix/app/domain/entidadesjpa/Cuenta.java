package com.ceuflix.app.domain.entidadesjpa;


import com.ceuflix.app.domain.cuenta.CuentaEntrada;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCuenta")
@Entity
@Table(name="cuenta")
public class Cuenta implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta")
    private Long idCuenta;

    @Column(name = "estado_cuenta")
    private Boolean estadoCuenta;

    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "img_cuenta")
    private String imgCuenta;

    @Column(name = "correo")
    private String correo;

    @Column(name="hash_contrasenia")
    private String hashContrasenia;

    @Column(name = "verificado")
    private Boolean verificado;

    @ManyToOne
    @JoinColumn(name = "tipo_cuenta")
    private TipoCuenta tipoCuenta;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

    @OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL)
    private List<Perfil> perfiles;

    public Cuenta(CuentaEntrada cuentaEntrada, TipoCuenta tipoCuenta,Persona persona) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        this.estadoCuenta=true;
        this.imgCuenta=cuentaEntrada.img_cuenta();
        this.correo=cuentaEntrada.correo();
        this.hashContrasenia=encoder.encode(cuentaEntrada.hash_contrasenia());
        //this.hashContrasenia=cuentaEntrada.hash_contrasenia();
        this.username= cuentaEntrada.username();
        this.verificado=false;
        this.tipoCuenta=tipoCuenta;
        this.persona=persona;
    }

    public void actualizarDatos(CuentaEntrada cuentaEntrada) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(cuentaEntrada.estado_cuenta() != null){
            this.estadoCuenta=cuentaEntrada.estado_cuenta();
        }
        if(cuentaEntrada.img_cuenta() != null){
            this.imgCuenta=cuentaEntrada.img_cuenta();
        }
        if(cuentaEntrada.correo() != null){
            this.correo=cuentaEntrada.correo();
        }
        if(cuentaEntrada.verificado() != null){
            this.verificado=cuentaEntrada.verificado();
        }
        if(cuentaEntrada.hash_contrasenia() != null){
            this.hashContrasenia=encoder.encode(cuentaEntrada.hash_contrasenia());
        }
        if(cuentaEntrada.username() != null){
            this.username=cuentaEntrada.username();
        }
    }
    public void desactivarCuenta() {
        this.estadoCuenta=false;
    }

    public void verificarCuenta(){
        this.verificado=true;
    }

    public void setTipoCuenta(TipoCuenta tipoCuenta) {
        this.tipoCuenta=tipoCuenta;
    }

    public Boolean verificarContrasenia(String ContraseniaEntrada){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(ContraseniaEntrada,this.hashContrasenia);
    }
    public Boolean agregarPerfil(Perfil perf) {
        int perfilesActivos = 0;
        for (Perfil perfil : perfiles) {
            if (perfil.getEstadoPerfil()) {
                perfilesActivos++;
            }
        }
        if (perfilesActivos < 5) {
            this.perfiles.add(perf);
            return true;
        }
        return false;
    }
    public Boolean eliminarPerfil(Perfil perf){
        perf.desactiva();
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String rol = getTipoCuenta().getNombre();
        return List.of(new SimpleGrantedAuthority(rol));
    }

    @Override
    public String getPassword() {
        return hashContrasenia;
    }

    @Override
    public String getUsername(){
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
