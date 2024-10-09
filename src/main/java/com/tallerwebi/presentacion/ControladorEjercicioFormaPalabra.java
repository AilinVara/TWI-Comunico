package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EjercicioFormaPalabra;
import com.tallerwebi.dominio.ServicioEjercicioFormaPalabra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorEjercicioFormaPalabra {

    @Autowired
    private ServicioEjercicioFormaPalabra servicioEjercicioVelocidad;

    private List<EjercicioFormaPalabra> ejercicios = new ArrayList<>();

    @Autowired
    private ServicioEjercicioFormaPalabra servicioEjercicioFormaPalabra;


    @RequestMapping(value = "/ejercicios-forma-palabra", method = RequestMethod.GET)
    public ModelAndView irAEjercicioFormaPalabra(@RequestParam (required = false, defaultValue = "1", value = "id") Long id, Model model, ModelMap modelMap){
        ModelMap modelo = new ModelMap();
        EjercicioFormaPalabra ejercicio = servicioEjercicioFormaPalabra.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicio);
        List<String> letras = servicioEjercicioFormaPalabra.convertirLetrasALista(ejercicio.getLetras());
        modelo.put("letras", letras);
        return new ModelAndView("ejercicios-forma-palabra", modelo);
    }

    @RequestMapping(value = "/verificarRespuesta", method = RequestMethod.POST)
    public ModelAndView verificarRespuesta(@RequestParam("listaLetras") String listaLetras, @RequestParam("ejercicioId") Long ejercicioId) {
        ModelMap modelo = new ModelMap();
        EjercicioFormaPalabra ejercicio = this.servicioEjercicioFormaPalabra.obtenerEjercicio(ejercicioId);
        Boolean resuelto = this.servicioEjercicioFormaPalabra.resolverEjercicio(ejercicio.getRespuestaCorrecta(), listaLetras);
        modelo.put("esCorrecta", resuelto);

        if (resuelto) {
            if (ejercicioId < 3) {
                EjercicioFormaPalabra siguienteEjercicio = this.servicioEjercicioFormaPalabra.obtenerEjercicio(ejercicio.getId() + 1);
                List<String> letras = servicioEjercicioFormaPalabra.convertirLetrasALista(siguienteEjercicio.getLetras());
                modelo.put("letras", letras);
                modelo.put("ejercicio", siguienteEjercicio);
            } else if (ejercicioId == 3) {
                modelo.put("mostrarVolverMenu", true);
                List<String> letras = servicioEjercicioFormaPalabra.convertirLetrasALista(ejercicio.getLetras());
                modelo.put("letras", letras);
                modelo.put("ejercicio", ejercicio);
            }
        } else {
            List<String> letras = servicioEjercicioFormaPalabra.convertirLetrasALista(ejercicio.getLetras());
            modelo.put("letras", letras);
            modelo.put("ejercicio", ejercicio);
        }

        return new ModelAndView("ejercicios-forma-palabra", modelo);
    }
}