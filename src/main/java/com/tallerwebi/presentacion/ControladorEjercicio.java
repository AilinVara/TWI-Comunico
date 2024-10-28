package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioVida servicioVida;
    private ServicioExperiencia servicioExperiencia;


    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida, ServicioExperiencia servicioExperiencia) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
        this.servicioExperiencia = servicioExperiencia;
    }

    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        modelo.put("leccion", leccionId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Ejercicio ejercicio = leccion.getEjercicios().get(indice - 1);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);
        agregarTiempoRestanteAlModelo(modelo,usuarioId);

        if (ejercicio instanceof EjercicioTraduccion) {
            return new ModelAndView("ejercicio", modelo);
        }else if(ejercicio instanceof EjercicioMatriz)
            return new ModelAndView("formaLetras", modelo);
        else if(ejercicio instanceof EjercicioFormaPalabra){
            EjercicioFormaPalabra ejercicioFormaPalabra = (EjercicioFormaPalabra) ejercicio;
            List<String> letras = servicioEjercicio.convertirLetrasALista(ejercicioFormaPalabra.getLetras());
            modelo.put("letras", letras);
            return new ModelAndView("ejercicios-forma-palabra", modelo);
        } else {
            return new ModelAndView("ejercicio-video", modelo);
        }
    }

    @RequestMapping(path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("respuesta") String respuesta, @RequestParam("ejercicioId") Long ejercicioId,
                                          @RequestParam("leccion") Long leccionId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        Boolean resuelto = false;
        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        if(ejercicio instanceof EjercicioTraduccion){
            resuelto = this.servicioEjercicio.resolverEjercicioTraduccion((EjercicioTraduccion) ejercicio, Long.parseLong(respuesta));

            mav.setViewName("ejercicio");
        }else if(ejercicio instanceof EjercicioMatriz){
            EjercicioMatriz ejercicioMatriz = (EjercicioMatriz) ejercicio;
            resuelto = this.servicioEjercicio.resolverEjercicioMatriz(respuesta, ejercicioMatriz.getPuntos());
            mav.setViewName("formaLetras");
        }else if(ejercicio instanceof  EjercicioFormaPalabra){
            EjercicioFormaPalabra ejercicioFormaPalabra = (EjercicioFormaPalabra) ejercicio;
            resuelto = this.servicioEjercicio.resolverEjercicioFormaPalabras(ejercicioFormaPalabra.getRespuestaCorrecta(), respuesta);
            mav.setViewName("ejercicios-forma-palabra");
        }else{
            resuelto = this.servicioEjercicio.resolverEjercicioTraduccionSenia((EjercicioTraduccionSenia) ejercicio, Long.parseLong(respuesta));
            mav.setViewName("ejercicio-video");
        }
        if (resuelto){
            this.servicioExperiencia.ganar100DeExperiencia(usuarioId);
        } else {

            this.servicioVida.perderUnaVida(usuarioId);
        }

        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        agregarTiempoRestanteAlModelo(modelo,usuarioId);
        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("esCorrecta", resuelto);
        mav.addAllObjects(modelo);
        return mav;
    }

    @RequestMapping(value = "/ejercicio-video", method = RequestMethod.GET)
    public ModelAndView irAEjercicioVideo(@RequestParam(required = false, defaultValue = "2", value = "id") Long id) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicioVideo);
        return new ModelAndView("ejercicio-video", modelo);
    }

    private void agregarTiempoRestanteAlModelo(ModelMap modelo, Long usuarioId) {
        Vida vida = this.servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
        long tiempoRestante = 60 - (segundosDesdeUltimaRegeneracion % 60); // Cada 60 segundos
        modelo.put("tiempoRestante", tiempoRestante);
    }

}

