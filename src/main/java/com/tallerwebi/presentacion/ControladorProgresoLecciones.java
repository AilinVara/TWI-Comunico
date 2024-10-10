package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProgresoLeccion;
import com.tallerwebi.dominio.ServicioProgresoLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorProgresoLecciones {

    private ServicioProgresoLeccion servicioProgresoLeccion;

    @Autowired
    public ControladorProgresoLecciones(ServicioProgresoLeccion servicioProgresoLeccion){
        this.servicioProgresoLeccion = servicioProgresoLeccion;
    }

    @RequestMapping(path = "/senias", method = RequestMethod.GET)
    public ModelAndView senias(){ return new ModelAndView("senias");}

    @RequestMapping(path = "/braille", method = RequestMethod.GET)
    public ModelAndView braille(HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Boolean leccion1;
        Long usuarioId = (Long) request.getSession().getAttribute("id");

        List<ProgresoLeccion> progresoLeccion = this.servicioProgresoLeccion.buscarProgresoLeccionDeUsuario(usuarioId, 1L);
        if(progresoLeccion.isEmpty()){
            leccion1 = false;
        } else{
            leccion1 = this.servicioProgresoLeccion.verificarCompletado(progresoLeccion);
        }

        Boolean leccion2;
        List<ProgresoLeccion> progresoLeccion2 = this.servicioProgresoLeccion.buscarProgresoLeccionDeUsuario(usuarioId, 2L);
        if(progresoLeccion2.isEmpty()){
            leccion2 = false;
        } else{
            leccion2 = this.servicioProgresoLeccion.verificarCompletado(progresoLeccion2);
        }

        modelo.put("leccion1", leccion1);
        modelo.put("leccion2", leccion2);
        modelo.put("leccion3", false);

        return new ModelAndView("braille", modelo);
    }

}
