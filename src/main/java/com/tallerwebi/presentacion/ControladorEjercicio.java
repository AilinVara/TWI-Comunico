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

    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private RepositorioUsuario repositorioUsuario;
    private ServicioVida servicioVida;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, RepositorioUsuario repositorioUsuario, ServicioVida servicioVida) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioVida =servicioVida;
    }


    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, @RequestParam ("usuarioId") Long usuarioID) {
        ModelMap modelo = new ModelMap();
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);

        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", leccion.getEjercicios().get(indice - 1));
        modelo.put("indice", indice);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping(path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("opcionSeleccionada") Long opcionId,
                                          @RequestParam("ejercicioId") Long ejercicioId, @RequestParam("leccion") Long leccionId,
                                          @RequestParam("usuarioId") Long usuarioId, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);


        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);

        if (!resuelto) {
            if (usuario != null) {
                boolean vidaPerdida = servicioEjercicio.perderVida(usuarioId);
                modelo.put("vidaPerdida", vidaPerdida);
            } else {
                modelo.put("Error", "No se encuentra el usuario");
            }
        }

        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("usuario", usuario);
        modelo.put("esCorrecta", resuelto);

        return new ModelAndView("ejercicio", modelo);
    }

}


