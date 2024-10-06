package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ProgresoLeccion;
import com.tallerwebi.dominio.ServicioProgresoLeccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
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
        Boolean ejercicio1;
        Long usuarioId = (Long) request.getSession().getAttribute("id");

        List<ProgresoLeccion> progresoLeccion = this.servicioProgresoLeccion.buscarProgresoLeccionDeUsuario(usuarioId, 1L);
        if(progresoLeccion.isEmpty()){
            ejercicio1 = false;
        } else{
            ejercicio1 = this.servicioProgresoLeccion.verificarCompletado(progresoLeccion);
        }
        modelo.put("ejercicio1", ejercicio1);
        modelo.put("ejercicio2", false);
        modelo.put("ejercicio3", false);

        return new ModelAndView("braille", modelo);
    }

}
