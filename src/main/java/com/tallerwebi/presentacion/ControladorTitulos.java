package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTitulos {

    @RequestMapping("/titulosUsuario")
    public ModelAndView mostrarTitulos() {
        return new ModelAndView("titulosUsuario");
    }
}
