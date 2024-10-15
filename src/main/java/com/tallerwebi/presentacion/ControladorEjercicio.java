package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;


@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioMatriz servicioMatriz;
    private ServicioVida servicioVida;


    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioMatriz servicioMatriz, ServicioVida servicioVida) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioMatriz = servicioMatriz;
        this.servicioVida = servicioVida;
    }


    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        modelo.put("leccion", leccionId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Long ejercicioId = Long.valueOf(indice);
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);
        agregarTiempoRestanteAlModelo(modelo,usuarioId);

        if (ejercicio.getId() >= 10) {
            Matriz matriz;
            matriz = this.servicioMatriz.obtenerMatrizPorEjercicio(ejercicio.getId());
            modelo.put("matriz", matriz);
            return new ModelAndView("formaLetras", modelo);
        }
        return new ModelAndView("ejercicio-braille", modelo);
    }

    @RequestMapping(path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId") Long ejercicioId,
                                          @RequestParam("leccion") Long leccionId, HttpServletRequest request) {

        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);

        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());

        if (!resuelto) {
            this.servicioVida.perderUnaVida(usuarioId);
            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        }

        agregarTiempoRestanteAlModelo(modelo,usuarioId);

        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio-braille", modelo);
    }

    @RequestMapping(path = "/resolverMatriz/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverMatriz(@PathVariable("indice") Long indice,
                                       @RequestParam("ejercicioId") Long ejercicioId,
                                       @RequestParam("leccion") Long leccionId,
                                       @RequestParam("matrizId") Long matrizId,
                                       @RequestParam("puntosSeleccionados") String puntosSeleccionados,
                                       HttpServletRequest request) {

        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Matriz matriz = this.servicioMatriz.obtenerMatriz(matrizId);

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);

        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        Boolean resuelto = this.servicioMatriz.resolverMatriz(puntosSeleccionados, matriz.getPuntos());
        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        modelo.put("esCorrecta", resuelto);
        modelo.put("leccion", leccionId);

        if (resuelto) {
            if (ejercicioId < 12) {
                Ejercicio siguienteEjercicio = this.servicioEjercicio.obtenerEjercicio(ejercicio.getId() + 1);
                Matriz siguienteMatriz = this.servicioMatriz.obtenerMatriz(matrizId + 1);
                modelo.put("matriz", siguienteMatriz);
                modelo.put("ejercicio", siguienteEjercicio);
            } else if (ejercicioId == 12) {
                modelo.put("matriz", matriz);
                modelo.put("mostrarVolverMenu", true);
                modelo.put("ejercicio", ejercicio);
            }
        } else {
            modelo.put("matriz", matriz);
            modelo.put("ejercicio", ejercicio);
            this.servicioVida.perderUnaVida(usuarioId);
            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        }

        agregarTiempoRestanteAlModelo(modelo,usuarioId);
        modelo.put("indice", indice);
        return new ModelAndView("formaLetras", modelo);
    }

    @RequestMapping(value = "/ejercicio-video", method = RequestMethod.GET)
    public ModelAndView irAEjercicioVideo(@RequestParam(required = false, defaultValue = "2", value = "id") Long id) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicioVideo);
        return new ModelAndView("ejercicio-video", modelo);
    }

    @RequestMapping(path = "/resolverVideo", method = RequestMethod.POST)
    public ModelAndView resolverEjercicioVideo(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId") Long ejercicioId, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicioVideo, opcionId);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());

        if (!resuelto) {
            this.servicioVida.perderUnaVida(usuarioId);
            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        }
        modelo.put("ejercicio", ejercicioVideo);
        modelo.put("esCorrecta", (resuelto));
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

