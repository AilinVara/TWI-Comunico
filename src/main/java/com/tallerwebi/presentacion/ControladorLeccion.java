package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ServicioLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;

@Controller
public class ControladorLeccion {

    private ServicioLeccion servicioLeccion;

    @Autowired
    public ControladorLeccion(ServicioLeccion servicioLeccion) {
        this.servicioLeccion = servicioLeccion;
    }

    @RequestMapping("/leccion/{id}")
    public ModelAndView leccion(@PathVariable Long id) {
        Leccion leccion = this.servicioLeccion.obtenerLeccion(id);
        return new ModelAndView("redirect:/ejercicio/1?leccion="+ leccion.getId());
    }
}
