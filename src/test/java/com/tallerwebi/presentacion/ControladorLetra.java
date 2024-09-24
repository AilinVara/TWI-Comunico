package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.ServicioLetra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorLetra {

    private ServicioLetra servicioLetra;

    @Autowired
    public ControladorLetra(ServicioLetra servicioLetra) {
        this.servicioLetra = servicioLetra;
    }

    // Muestra la página de búsqueda de letras
    @RequestMapping("/letra")
    public ModelAndView irALetra() {
        ModelMap modelo = new ModelMap();
        modelo.put("mensaje", "Buscar una letra por nombre o ID.");
        return new ModelAndView("letra", modelo);
    }

    // Busca una letra por ID
    @RequestMapping(path = "/buscarLetraPorId", method = RequestMethod.GET)
    public ModelAndView buscarLetraPorId(@RequestParam("id") Long id) {
        ModelMap modelo = new ModelMap();
        Letra letra = this.servicioLetra.buscarLetraPorId(id);

        if (letra != null) {
            modelo.put("letra", letra);
        } else {
            modelo.put("mensaje", "Letra no encontrada.");
        }

        return new ModelAndView("letra", modelo);
    }

    // Busca letras por nombre
    @RequestMapping(path = "/buscarLetraPorNombre", method = RequestMethod.GET)
    public ModelAndView buscarLetraPorNombre(@RequestParam("nombre") String nombre) {
        ModelMap modelo = new ModelMap();
        List<Letra> letras = (List<Letra>) this.servicioLetra.buscarPorNombre(nombre);

        if (!letras.isEmpty()) {
            modelo.put("letras", letras);
        } else {
            modelo.put("mensaje", "No se encontraron letras con ese nombre.");
        }

        return new ModelAndView("letra", modelo);
    }
}
