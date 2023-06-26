package com.ceuflix.app.controller.api;

import com.ceuflix.app.domain.repository.PeliculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class HelloController {
    @Autowired
    PeliculaRepository peliculaRepository;

    @GetMapping
    public String helloWorld(){
        return "hello world";
    }

}
