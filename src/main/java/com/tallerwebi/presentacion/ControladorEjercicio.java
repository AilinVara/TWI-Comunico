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
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Ejercicio ejercicio = leccion.getEjercicios().get(indice - 1);
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);
        agregarTiempoRestanteAlModelo(modelo,usuarioId);

        if (ejercicio instanceof EjercicioTraduccion) {
            return new ModelAndView("ejercicio", modelo);
        }else{
            return new ModelAndView("formaLetras", modelo);
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
        }else{
            EjercicioMatriz ejercicioMatriz = (EjercicioMatriz) ejercicio;
            resuelto = this.servicioEjercicio.resolverEjercicioMatriz(respuesta, ejercicioMatriz.getPuntos());
            mav.setViewName("formaLetras");
        }
        if (!resuelto) {
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

//    @RequestMapping(path = "/resolverMatriz/{indice}", method = RequestMethod.POST)
//    public ModelAndView resolverMatriz(@PathVariable("indice") Long indice,
//                                       @RequestParam("ejercicioId") Long ejercicioId,
//                                       @RequestParam("leccion") Long leccionId,
//                                       @RequestParam("matrizId") Long matrizId,
//                                       @RequestParam("puntosSeleccionados") String puntosSeleccionados,
//                                       HttpServletRequest request) {
//
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicioTraduccion = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
//        EjercicioMatriz ejercicioMatriz = this.servicioMatriz.obtenerMatriz(matrizId);
//
//        Long usuarioId = (Long) request.getSession().getAttribute("id");
//        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
//
//        if (progreso == null) {
//            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
//            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
//        }
//
//        Boolean resuelto = this.servicioMatriz.resolverMatriz(puntosSeleccionados, ejercicioMatriz.getPuntos());
//        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);
//        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
//        modelo.put("esCorrecta", resuelto);
//        modelo.put("leccion", leccionId);
//
//        if (resuelto) {
//            if (ejercicioId < 12) {
//                EjercicioTraduccion siguienteEjercicioTraduccion = this.servicioEjercicio.obtenerEjercicio(ejercicioTraduccion.getId() + 1);
//                EjercicioMatriz siguienteEjercicioMatriz = this.servicioMatriz.obtenerMatriz(matrizId + 1);
//                modelo.put("matriz", siguienteEjercicioMatriz);
//                modelo.put("ejercicio", siguienteEjercicioTraduccion);
//            } else if (ejercicioId == 12) {
//                modelo.put("matriz", ejercicioMatriz);
//                modelo.put("mostrarVolverMenu", true);
//                modelo.put("ejercicio", ejercicioTraduccion);
//            }
//        } else {
//            modelo.put("matriz", ejercicioMatriz);
//            modelo.put("ejercicio", ejercicioTraduccion);
//            this.servicioVida.perderUnaVida(usuarioId);
//            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
//        }
//
//        agregarTiempoRestanteAlModelo(modelo,usuarioId);
//        modelo.put("indice", indice);
//        return new ModelAndView("formaLetras", modelo);
//    }

    @RequestMapping(value = "/ejercicio-video", method = RequestMethod.GET)
    public ModelAndView irAEjercicioVideo(@RequestParam(required = false, defaultValue = "2", value = "id") Long id) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicioVideo);
        return new ModelAndView("ejercicio-video", modelo);
    }

//    @RequestMapping(path = "/resolverVideo", method = RequestMethod.POST)
//    public ModelAndView resolverEjercicioVideo(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId") Long ejercicioId, HttpServletRequest request) {
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicioTraduccionVideo = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
//        Long usuarioId = (Long) request.getSession().getAttribute("id");
//        Boolean resuelto = this.servicioEjercicio.resolverEjercicioTraduccion(ejercicioTraduccionVideo, opcionId);
//        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
//
//        if (!resuelto) {
//            this.servicioVida.perderUnaVida(usuarioId);
//            modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
//        }
//        modelo.put("ejercicio", ejercicioTraduccionVideo);
//        modelo.put("esCorrecta", (resuelto));
//        return new ModelAndView("ejercicio-video", modelo);
//    }

    private void agregarTiempoRestanteAlModelo(ModelMap modelo, Long usuarioId) {
        Vida vida = this.servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
        long tiempoRestante = 60 - (segundosDesdeUltimaRegeneracion % 60); // Cada 60 segundos
        modelo.put("tiempoRestante", tiempoRestante);
    }

}

