package com.ceuflix.app.controller.api;

import com.ceuflix.app.domain.entidadesjpa.Genero;
import com.ceuflix.app.domain.entidadesjpa.Pelicula;
import com.ceuflix.app.domain.pelicula.*;
import com.ceuflix.app.domain.repository.GeneroRepository;
import com.ceuflix.app.domain.repository.PeliculaRepository;
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
@RequestMapping("/api/pelicula")
public class PeliculaController {
    @Autowired
    private PeliculaRepository peliculaRepository;

    @Autowired
    private GeneroRepository generoRepository;

    @PostMapping//request para recibir lo que se manda en json y valid para validar en el DTO
    public ResponseEntity<PeliculaSalida> postPelicula(@RequestBody @Valid PeliculaEntradaPostGet peli, UriComponentsBuilder uriComponentsBuilder){
        Pelicula pelicula = new Pelicula(peli);
        peliculaRepository.save(pelicula);
        PeliculaSalida peliculaSalida= new PeliculaSalida(pelicula);
        URI url = UriComponentsBuilder.fromUriString("pelicula/{id}")
                .buildAndExpand(Map.of("id", peliculaSalida.id_pelicula()))
                .toUri();
        return ResponseEntity.created(url).body(peliculaSalida);
    }
    @PostMapping("/Genero")
    public ResponseEntity postGeneroPelicula(EntradaPeliculaGenero entradaPeliculaGenero){
        Pelicula pelicula=peliculaRepository.findByNombrePelicula(entradaPeliculaGenero.nombre_pelicula());
        Genero genero=generoRepository.findByNombreGenero(entradaPeliculaGenero.nombre_genero());
        PeliculaSalida peliculaSalida = new PeliculaSalida(pelicula);

        pelicula.agregarGenero(genero);
        return ResponseEntity.ok(peliculaSalida);
    }
    @DeleteMapping("/Genero")
    public ResponseEntity deleteGenero(EntradaPeliculaGenero entradaPeliculaGenero){
        Pelicula pelicula=peliculaRepository.findByNombrePelicula(entradaPeliculaGenero.nombre_pelicula());
        Genero genero=generoRepository.findByNombreGenero(entradaPeliculaGenero.nombre_genero());

        pelicula.eliminarGenero(genero);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<PeliculaSalida>> getPelicula(Pageable paginacion){
        return ResponseEntity.ok(peliculaRepository.findByEstadoPeliculaTrue(paginacion).map(PeliculaSalida::new));
    }

    @GetMapping("/{nombre}")
    public ResponseEntity getPeliculasPorNombre(@PathVariable String nombre,Pageable paginacion){
        return ResponseEntity.ok(peliculaRepository.findByEstadoPeliculaTrueAndNombrePeliculaContaining(paginacion, nombre).map(PeliculaSalida::new));
    }


    @PutMapping @Transactional//necesita transactional
    public ResponseEntity actualizaPelicula(@RequestBody @Valid PeliculaEntradaPut peli){
        Pelicula pelicula = peliculaRepository.getReferenceById(peli.id());
        pelicula.actualizarDatos(peli);
        PeliculaSalida peliculaSalida=new PeliculaSalida(pelicula);
        return ResponseEntity.ok(peliculaSalida);
    }

    @Transactional
    @DeleteMapping//exclusion logica
    public ResponseEntity eliminarPelicula(@RequestBody @Valid PeliculaEntradaPut peli){
        Pelicula pelicula = peliculaRepository.getReferenceById(peli.id());
        pelicula.desactivarPelicula();
        return ResponseEntity.noContent().build();
    }


    /* Eliminar permanente --no probado xd
    @Transactional
    @DeleteMapping//exclusion logica
    public void eliminarPelicula(@RequestBody @Valid PeliculaEntradaPut peli){
        Pelicula pelicula = peliculaRepository.getReferenceById(peli.id());
        peliculaRepository.delete(pelicula);
    }

     */
}
