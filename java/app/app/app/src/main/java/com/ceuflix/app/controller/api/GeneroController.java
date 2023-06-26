package com.ceuflix.app.controller.api;

import com.ceuflix.app.domain.entidadesjpa.Genero;
import com.ceuflix.app.domain.entidadesjpa.Pelicula;
import com.ceuflix.app.domain.pelicula.*;
import com.ceuflix.app.domain.repository.GeneroRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/genero")
public class GeneroController {

    @Autowired
    private GeneroRepository generoRepository;

    @GetMapping
    public ResponseEntity<Page<GeneroSalida>> getGeneros(Pageable paginacion){
        /*
        regreasa Todalainfo
        return peliculaRepository.findAll(paginacion).map(PeliculaSalida::new);
         */
        ;
        return ResponseEntity.ok(generoRepository.findByEstadoGeneroTrue(paginacion).map(GeneroSalida::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity<GeneroSalida> getUnGenero(@PathVariable Long id){
        Genero genero = generoRepository.getReferenceById(id);
        GeneroSalida generoSalida=new GeneroSalida(genero);
        return ResponseEntity.ok(generoSalida);
    }

    @PostMapping//request para recibir lo que se manda en json y valid para validar en el DTO
    public ResponseEntity<GeneroSalida> postPelicula(@RequestBody @Valid GeneroEntrada generoEntrada, UriComponentsBuilder uriComponentsBuilder){
        Genero genero = new Genero(generoEntrada);
        generoRepository.save(genero);

        GeneroSalida generoSalida= new GeneroSalida(genero);
        URI url = UriComponentsBuilder.fromUriString("genero/{id}")
                .buildAndExpand(Map.of("id", generoSalida.nombre_genero()))
                .toUri();
        return ResponseEntity.created(url).body(generoSalida);
    }

    @PutMapping @Transactional//necesita transactional
    public ResponseEntity actualizaPelicula(@RequestBody @Valid GeneroEntrada generoEntrada){
        Genero genero = generoRepository.getReferenceById(generoEntrada.id_genero());
        genero.actualizarDatos(generoEntrada);
        GeneroSalida generoSalida=new GeneroSalida(genero);
        return ResponseEntity.ok(generoSalida);
    }

    @Transactional
    @DeleteMapping
    public void eliminarPelicula(@RequestBody GeneroEntrada generoEntrada){
        Genero genero = generoRepository.getReferenceById(generoEntrada.id_genero());
        genero.desactivarGenero();
        ResponseEntity.noContent();
    }


}
