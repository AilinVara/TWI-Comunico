package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ExpresionSenias;
import com.tallerwebi.dominio.ServicioExpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
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

        agregarRutas("src/main/webapp/resources/core/img/Como-estas-senias.png");
        agregarNombres("Como estas");

    }

    public void agregarRutas(String ruta) {
        routes.add(ruta);
    }
    public void agregarNombres(String nombre) {
        names.add(nombre);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView cargarImagenesDeExpresion() throws IOException {
        List<ExpresionSenias> expresions = this.servicioExpresion.listarExpresionSenias();


        if(expresions.isEmpty()) {
            for(int i = 0; i < routes.size() &&  i < names.size(); i++) {
                this.servicioExpresion.guardarExpresionSenias(names.get(i), routes.get(i));
            }
        }

        return null;
    }
}
