package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.ExpresionSenias;
import com.tallerwebi.dominio.ServicioExpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorExpresionSenias {

    private ServicioExpresion servicioExpresion;
    private ArrayList<String> routes;
    private ArrayList<String> names;

    @Autowired
    public ControladorExpresionSenias(ServicioExpresion servicioExpresion) {
        this.servicioExpresion = servicioExpresion;

        agregarRutas("Como-estas-senias.png");
        agregarRutas("Disculpas-senias.png");
        agregarRutas("Gracias-senias.png");
        agregarRutas("Mucho-senias.png");
        agregarRutas("Saludos-senias.png");
        agregarRutas("Te-amo-senias.png");

        agregarNombres("Como estas");
        agregarNombres("Disculpas");
        agregarNombres("Gracias");
        agregarNombres("Mucho");
        agregarNombres("Saludos");
        agregarNombres("Te amo");
    }

    public void agregarRutas(String ruta) {
        routes.add(ruta);
    }
    public void agregarNombres(String nombre) {
        names.add(nombre);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView cargarImagenesDeExpresion() {
        List<ExpresionSenias> expresions = this.servicioExpresion.listarExpresionSenias();

        if(expresions.isEmpty()) {

        }

        return null;
    }
}
