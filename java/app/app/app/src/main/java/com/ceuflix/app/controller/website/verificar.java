package com.ceuflix.app.controller.website;

import com.ceuflix.app.controller.api.CuentaController;
import com.ceuflix.app.domain.entidadesjpa.Cuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller()
public class verificar {
    @Autowired
    CuentaController cuentaController;

    @GetMapping("/verificar/{id}")
    public String verificarGet(@PathVariable Long id, Model model){
        Cuenta cuenta = cuentaController.verificarCuenta(id);
        model.addAttribute("id",cuenta.getIdCuenta());
        model.addAttribute("nombre",String.format("%s %s",cuenta.getPersona().getNombre(),cuenta.getPersona().getApellido()));
        model.addAttribute("correo",cuenta.getCorreo());
        return "verificado";
    }
}
