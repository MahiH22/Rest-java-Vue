package com.ceuflix.app.controller.api;

import com.ceuflix.app.controller.mail.MailSenderService;
import com.ceuflix.app.domain.cuenta.*;
import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import com.ceuflix.app.domain.entidadesjpa.Perfil;
import com.ceuflix.app.domain.entidadesjpa.Persona;
import com.ceuflix.app.domain.entidadesjpa.TipoCuenta;
import com.ceuflix.app.domain.repository.CuentaRepository;
import com.ceuflix.app.domain.repository.PerfilRepository;
import com.ceuflix.app.domain.repository.PersonaRepository;
import com.ceuflix.app.domain.repository.TipoCuentaRepository;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/cuenta")
public class CuentaController {

    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private TipoCuentaRepository tipoCuentaRepository;
    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private MailSenderService mailSenderService;


    @GetMapping
    public ResponseEntity getCuenta(Pageable paginacion){
        return ResponseEntity.ok(cuentaRepository.findByEstadoCuentaTrue(paginacion).map(CuentaSalida::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity <CuentaSalida> getUnaCuenta(@PathVariable Long id){
        Cuenta cuenta = cuentaRepository.getReferenceById(id);
        CuentaSalida cuentaSalida=new CuentaSalida(cuenta);
        return ResponseEntity.ok(cuentaSalida);
    }
    @PostMapping("/perfil") @Transactional
    public ResponseEntity crearPerfil(@RequestBody PerfilEntrada perfilEntrada){
        Cuenta cuenta= cuentaRepository.getReferenceById(perfilEntrada.id_cuenta());
        Perfil perfil = new Perfil(perfilEntrada,cuenta);
        if(cuenta.agregarPerfil(perfil)){
            PerfilSalida perfilSalida= new PerfilSalida(perfil);
            return ResponseEntity.ok(perfilSalida);
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/perfil") @Transactional
    public ResponseEntity eliminarPerfil(@RequestBody PerfilEntrada perfilEntrada){
        Perfil perfil=perfilRepository.getReferenceById(perfilEntrada.id_perfil());
        perfil.desactiva();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/perfil/{id}")
    public ResponseEntity<List<PerfilSalida>> listarPerfiles(@PathVariable Long id, Pageable paginacion) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        if (cuentaOptional.isPresent()) {
            Cuenta cuenta = cuentaOptional.get();
            List<PerfilSalida> perfilesSalida = cuenta.getPerfiles().stream()
                    .filter(Perfil::getEstadoPerfil)
                    .map(PerfilSalida::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(perfilesSalida);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/perfil") @Transactional
    public ResponseEntity editarPerfil(@RequestBody PerfilEntrada perfilEntrada){
        Optional<Perfil> perfilOptional = perfilRepository.findById(perfilEntrada.id_perfil());
        if (perfilOptional.isPresent()) {
            Perfil perfil = perfilOptional.get();
            perfil.actualizar(perfilEntrada);
            PerfilSalida perfilSalida= new PerfilSalida(perfil);
            return ResponseEntity.ok(perfilSalida);
        }
        return ResponseEntity.badRequest().build();
    }
    @PostMapping @Transactional
    public ResponseEntity crearCuentaYPersonas(@RequestBody @Valid CuentaEntrada cuentaEntrada, UriComponentsBuilder uriComponentsBuilder){
        TipoCuenta tipoCuenta= tipoCuentaRepository.findById(Long.valueOf("2"))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de cuenta no encontrado"));
        Persona persona = new Persona(cuentaEntrada);
        personaRepository.save(persona);

        Cuenta cuenta = new Cuenta(cuentaEntrada,tipoCuenta,persona);
        cuentaRepository.save(cuenta);

        correoVerificar(cuenta.getCorreo(),cuenta);
        CuentaSalida cuentaSalida=new CuentaSalida(cuenta);
        URI url = UriComponentsBuilder.fromUriString("cuenta/{id}")
                .buildAndExpand(Map.of("id", cuentaSalida.id_cuenta()))
                .toUri();
        return ResponseEntity.created(url).body(cuentaSalida);
    }
    @PutMapping @Transactional//necesita transactional
    public ResponseEntity actualizaCuenta(@RequestBody @Valid CuentaEntrada cuentaEntrada){
        Cuenta cuenta = cuentaRepository.getReferenceById(cuentaEntrada.id_cuenta());
        Persona persona = cuenta.getPersona();

        cuenta.actualizarDatos(cuentaEntrada);
        if(cuentaEntrada.persona() != null){
            persona.actualizarDatos(cuentaEntrada);
        }

        if (cuentaEntrada.tipo_cuenta() != null) {
            TipoCuenta tipoCuenta = new TipoCuenta();
            tipoCuenta=tipoCuentaRepository.getReferenceById(cuentaEntrada.tipo_cuenta());
            cuenta.setTipoCuenta(tipoCuenta);
        }

        CuentaSalida cuentaSalida=new CuentaSalida(cuenta);
        return ResponseEntity.ok(cuentaSalida);
    }
    @Transactional
    @DeleteMapping//exclusion logica
    public ResponseEntity eliminarCuenta(@RequestBody @Valid CuentaEntrada cuentaEntrada){
        Cuenta cuenta = cuentaRepository.getReferenceById(cuentaEntrada.id_cuenta());
        cuenta.desactivarCuenta();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/tipoCuenta")
    public ResponseEntity tipoCuenta(Pageable paginacion){
    return ResponseEntity.ok(tipoCuentaRepository.findAll(paginacion).map(TipoCuentaSalida::new));
    }


    @Transactional
    public Cuenta verificarCuenta(Long id){
        Optional<Cuenta> optionalCuenta = cuentaRepository.findById(id);
        if (optionalCuenta.isPresent()) {
            Cuenta cuenta = optionalCuenta.get();
            cuenta.verificarCuenta();
            return cuenta;
        } else {
            return null;
        }
    }


    public void correoVerificar(String correo,Cuenta cuenta)  {
        try {
            mailSenderService.verificarCorreo(correo,"Verificar la cuenta",cuenta);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
