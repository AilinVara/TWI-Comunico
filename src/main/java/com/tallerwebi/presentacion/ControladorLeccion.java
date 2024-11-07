package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class ControladorLeccion {

    private ServicioVida servicioVida;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioLeccion servicioLeccion;

    @Autowired
    public ControladorLeccion(ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida) {
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
    }

    @RequestMapping("/leccion/{id}")
    public ModelAndView leccion(@PathVariable Long id) {
        Leccion leccion = this.servicioLeccion.obtenerLeccion(id);
        return new ModelAndView("redirect:/ejercicio/1?leccion=" + leccion.getId());
    }

    @RequestMapping("/braille/lecciones/{tipo}")
    public ModelAndView obtenerLecciones(@PathVariable("tipo") String tipo, HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Integer vidas = this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
        Map<Long, Boolean> leccionesDesbloqueadas = this.servicioProgresoLeccion.buscarProgresoPorTipoEjercicioConEstado(tipo, usuarioId);

        boolean desbloqueado = true;

        for (Map.Entry<Long, Boolean> entry : leccionesDesbloqueadas.entrySet()) {
            Long leccionId = entry.getKey();
            boolean completado = entry.getValue();

            leccionesDesbloqueadas.put(leccionId, desbloqueado);
            desbloqueado = completado;
        }

        switch (tipo){
            case "matriz":
                tipo = "Formá letras";
                break;
            case "traduccion":
                tipo = "Reconocé letras";
                break;
            case "forma-palabras":
                tipo = "Formá palabras";
                break;
        }
        modelo.put("vidas", vidas);
        modelo.put("tipo", tipo);
        modelo.put("progresos", leccionesDesbloqueadas);
        return new ModelAndView("mapa-lecciones", modelo);
    }

    @RequestMapping("/desafio-velocidad")
    public ModelAndView desafioVelocidad(HttpServletRequest request) {
        return new ModelAndView("desafio-velocidad");
    }

}
