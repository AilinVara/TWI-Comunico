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

import static org.hsqldb.lib.StringUtil.isEmpty;

@Controller
public class ControladorLetra {

private ServicioLetra servicioLetra;

@Autowired
public ControladorLetra(ServicioLetra servicioLetra) {
    this.servicioLetra = servicioLetra;
}

@RequestMapping("braille/alfabeto")
public ModelAndView alfabetoBraille(@RequestParam(required = false, defaultValue = "", value="letra") String letraBuscada) {
    ModelMap modelo = new ModelMap();
    List<Letra> letras = new ArrayList<>();

    if (isEmpty(letraBuscada)) {
        letras = servicioLetra.buscarTodasLasLetras();
        modelo.put("letrasbraille", letras);
        return new ModelAndView("alfabeto", modelo);
    } else {
        Letra letra = servicioLetra.buscarPorNombre(letraBuscada);
        if (letra != null)
            letras.add(letra);
        else
            letras = servicioLetra.buscarTodasLasLetras();
        modelo.put("letrasbraille", letras);
        return new ModelAndView("alfabeto", modelo);
    }
}


@RequestMapping("senias/alfabeto")
public ModelAndView alfabetoSenias(@RequestParam(required = false, defaultValue = "", value="letra") String letraBuscada) {
    ModelMap modelo = new ModelMap();
    List<Letra> letras = new ArrayList<>();

    if (isEmpty(letraBuscada)) {
        letras = servicioLetra.buscarTodasLasLetras();
        modelo.put("letrassenias", letras);
        return new ModelAndView("alfabeto", modelo);
    } else {
        Letra letra = servicioLetra.buscarPorNombre(letraBuscada);
        if (letra != null)
            letras.add(letra);
        else
            letras = servicioLetra.buscarTodasLasLetras();
        modelo.put("letrassenias", letras);
        return new ModelAndView("alfabeto", modelo);
    }
}

}
