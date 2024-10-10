package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ControladorEjercicio {

    private ServicioVida servicioVida;
    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioProgresoLeccion servicioProgresoLeccion;


    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
    }


    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");


        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", leccion.getEjercicios().get(indice - 1));
        modelo.put("indice", indice);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping(path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("opcionSeleccionada") Long opcionId,
                                          @RequestParam("ejercicioId") Long ejercicioId, @RequestParam("leccion") Long leccionId,
                                          HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");

        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);

        if (!resuelto) {
            this.servicioVida.perderUnaVida(usuarioId);
            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        }

        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("esCorrecta", resuelto);

        return new ModelAndView("ejercicio", modelo);
    }

}


