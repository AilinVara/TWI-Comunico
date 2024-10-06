package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ServicioEjercicio;
import com.tallerwebi.dominio.ServicioLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion) {
            this.servicioEjercicio = servicioEjercicio;
            this.servicioLeccion = servicioLeccion;
    }

//    @RequestMapping(value = "/ejercicio", method = RequestMethod.GET)
//    public ModelAndView irAjercicio(){
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(1L);
//        modelo.put("ejercicio", ejercicio);
//        return new ModelAndView("ejercicio", modelo);
//    }

    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice
    ){
        ModelMap modelo = new ModelMap();

        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        switch (indice) {
            case 1:
                modelo.put("ejercicio", leccion.getEjercicios().get(0));
                modelo.put("indice", indice);
                break;
            case 2:
                modelo.put("ejercicio", leccion.getEjercicios().get(1));
                indice++;
                modelo.put("indice", indice);
                break;
            default:
                modelo.put("ejercicio", leccion.getEjercicios().get(2));
                indice++;
                modelo.put("indice", indice);
                return new ModelAndView("redirect:/braille", modelo);
        }
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping( path = "/resolver", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId")Long ejercicioId){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        modelo.put("ejercicio",ejercicio);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio", modelo);
    }
}
