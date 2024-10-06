package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioLogin servicioUsuario;
    private ServicioProgresoLeccion servicioProgresoLeccion;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioLogin servicioUsuario, ServicioProgresoLeccion servicioProgresoLeccion) {
            this.servicioEjercicio = servicioEjercicio;
            this.servicioLeccion = servicioLeccion;
            this.servicioUsuario = servicioUsuario;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
    }

//    @RequestMapping(value = "/ejercicio", method = RequestMethod.GET)
//    public ModelAndView irAjercicio(){
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(1L);
//        modelo.put("ejercicio", ejercicio);
//        return new ModelAndView("ejercicio", modelo);
//    }

    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Usuario usuario = this.servicioUsuario.obtenerUsuarioPorId((Long) request.getSession().getAttribute("id"));
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        modelo.put("leccion", leccionId);
        modelo.put("usuario", usuario.getEmail());
        modelo.put("ejercicio", leccion.getEjercicios().get(indice-1));
        modelo.put("indice", indice);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping( path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId")Long ejercicioId,
                                          @RequestParam("leccion")Long leccionId, @PathVariable("indice") Long indice, HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, ejercicioId, usuarioId);

        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        modelo.put("ejercicio",ejercicio);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio", modelo);
    }
}
