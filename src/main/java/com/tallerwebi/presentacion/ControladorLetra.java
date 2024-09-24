package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.ServicioLetra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorLetra {

    private ServicioLetra servicioLetra;

    @Autowired
    public ControladorLetra(ServicioLetra servicioLetra) {
        this.servicioLetra = servicioLetra;
    }

    // Muestra la página de búsqueda de letras
    @RequestMapping("/alfabeto")
    public ModelAndView alfabeto(@RequestParam(required = false, defaultValue = "", value="letra") String letraBuscada) {
        ModelMap modelo = new ModelMap();

        List<Letra> letras = new ArrayList<>();

        if(letraBuscada.equals("")){
            letras = servicioLetra.buscarTodasLasLetras();
            modelo.put("letras", letras);

            return new ModelAndView("alfabeto", modelo);
        }else{
            Letra letra = servicioLetra.buscarPorNombre(letraBuscada);
            letras.add(letra);
            modelo.put("letras", letras);

            return new ModelAndView("alfabeto", modelo);
        }
    }

}
