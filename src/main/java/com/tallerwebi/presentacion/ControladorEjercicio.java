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
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioMatriz servicioMatrizMock) {
            this.servicioEjercicio = servicioEjercicio;
            this.servicioLeccion = servicioLeccion;
            this.servicioProgresoLeccion = servicioProgresoLeccion;
            this.servicioMatriz = servicioMatrizMock;
    }


    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice){
        ModelMap modelo = new ModelMap();
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", leccion.getEjercicios().get(indice-1));
        modelo.put("indice", indice);
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

    @RequestMapping(value = "/ejercicio-video", method = RequestMethod.GET)
    public ModelAndView irAEjercicioVideo(@RequestParam(required = false, defaultValue = "2", value = "id") Long id){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicioVideo);
        return new ModelAndView("ejercicio-video", modelo);
    }

    @RequestMapping(path = "/resolverVideo", method = RequestMethod.POST)
    public ModelAndView resolverEjercicioVideo(@RequestParam("opcionSeleccionada") Long opcionId,@RequestParam("ejercicioId")Long ejercicioId){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicioVideo, opcionId);
        modelo.put("ejercicio",ejercicioVideo);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio-video", modelo);
    }

}
