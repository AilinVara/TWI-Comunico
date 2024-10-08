package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ServicioLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


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
        Long ejercicio = null;
        int idLeccion = Math.toIntExact(id);
        switch (idLeccion){
            case 1: ejercicio = 1L;
                    break;
            case 2: ejercicio = 10L;
                    break;
            case 3: ejercicio = 20L;
                    break;
        }
        return new ModelAndView("redirect:/ejercicio/" + ejercicio + "?leccion=" + leccion.getId());
    }
}
