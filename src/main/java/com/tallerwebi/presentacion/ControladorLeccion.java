package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ServicioLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        ModelMap model = new ModelMap();

        Leccion leccion = this.servicioLeccion.obtenerLeccion(id);

        model.put("ejercicios", leccion.getEjercicios());

        return new ModelAndView("leccion", model);
    }
}
