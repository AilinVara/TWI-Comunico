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
    private ServicioMatriz servicioMatriz;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioMatriz servicioMatriz) {
            this.servicioEjercicio = servicioEjercicio;
            this.servicioLeccion = servicioLeccion;
            this.servicioProgresoLeccion = servicioProgresoLeccion;
            this.servicioMatriz = servicioMatriz;
    }


    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice){
        ModelMap modelo = new ModelMap();
        modelo.put("leccion", leccionId);
        Long ejercicioId = Long.valueOf(indice);
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);

        if(ejercicio.getId() >= 10){
            Matriz matriz;
            matriz = this.servicioMatriz.obtenerMatrizPorEjercicio(ejercicio.getId());
            modelo.put("matriz",matriz);
            return new ModelAndView("formaLetras", modelo);
        }
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping( path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId")Long ejercicioId,
                                          @RequestParam("leccion")Long leccionId, HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);

        if(progreso == null){
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio",ejercicio);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping(path = "/resolverMatriz/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverMatriz(@PathVariable("indice") Long indice,
                                       @RequestParam("ejercicioId")Long ejercicioId,
                                       @RequestParam("leccion")Long leccionId,
                                       @RequestParam("matrizId")Long matrizId,
                                       @RequestParam("puntosSeleccionados") String puntosSeleccionados,
                                       HttpServletRequest request){

        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Matriz matriz = this.servicioMatriz.obtenerMatriz(matrizId);

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);

        if(progreso == null){
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        Boolean resuelto = this.servicioMatriz.resolverMatriz(puntosSeleccionados, matriz.getPuntos());
        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);
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
        }

        modelo.put("indice", indice);
        return new ModelAndView("formaLetras", modelo);
    }


}
