package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.ServicioEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio) {
            this.servicioEjercicio = servicioEjercicio;
    }

//    @RequestMapping(value = "/ejercicio", method = RequestMethod.GET)
//    public ModelAndView irAjercicio(){
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(1L);
//        modelo.put("ejercicio", ejercicio);
//        return new ModelAndView("ejercicio", modelo);
//    }

    @RequestMapping(value = "/ejercicio", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam(required = false, defaultValue = "1", value = "id") Long id){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicio);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping( path = "/resolver", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId")Long ejercicioId){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        modelo.put("ejercicio",ejercicio);
        modelo.put("esCorrecta", (ejercicio.getOpcionCorrecta().getId().equals(opcionId)));
        return new ModelAndView("ejercicio", modelo);
    }
}
