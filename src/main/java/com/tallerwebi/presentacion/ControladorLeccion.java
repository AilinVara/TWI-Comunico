package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class ControladorLeccion {

    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioLeccion servicioLeccion;

    @Autowired
    public ControladorLeccion(ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion) {
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
    }

    @RequestMapping("/leccion/{id}")
    public ModelAndView leccion(@PathVariable Long id) {
        Leccion leccion = this.servicioLeccion.obtenerLeccion(id);
        return new ModelAndView("redirect:/ejercicio/1?leccion=" + leccion.getId());
    }

    @RequestMapping("/braille/lecciones/traduccion")
    public ModelAndView leccionesTraduccion(HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        List<ProgresoLeccion> progresosTraduccion = this.servicioProgresoLeccion.buscarProgresoPorTipoEjercicio("traduccion", usuarioId);

        Map<Long, Boolean> leccionesCompletadas = new TreeMap<>();

        for (ProgresoLeccion progreso : progresosTraduccion) {
            Long leccionId= progreso.getLeccion().getId();
            Boolean completado = this.servicioProgresoLeccion.verificarCompletadoPorLeccion(progreso.getLeccion().getId(), usuarioId);
            leccionesCompletadas.put(leccionId, completado);
        }

        boolean desbloqueado = true;
        Map<Long, Boolean> leccionesDesbloqueadas = new TreeMap<>();

        for (Map.Entry<Long, Boolean> entry : leccionesCompletadas.entrySet()) {
            Long leccionId = entry.getKey();
            boolean completado = entry.getValue();

            leccionesDesbloqueadas.put(leccionId, desbloqueado);
            desbloqueado = completado;
        }

        modelo.put("progresos", leccionesDesbloqueadas);
        return new ModelAndView("mapa-braille-traduccion", modelo);
    }

    @RequestMapping("/braille/lecciones/matriz")
    public ModelAndView leccionesMatriz(HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        List<ProgresoLeccion> progresosTraduccion = this.servicioProgresoLeccion.buscarProgresoPorTipoEjercicio("matriz", usuarioId);

        Map<Long, Boolean> leccionesCompletadas = new TreeMap<>();

        for (ProgresoLeccion progreso : progresosTraduccion) {
            Long leccionId= progreso.getLeccion().getId();
            Boolean completado = this.servicioProgresoLeccion.verificarCompletadoPorLeccion(progreso.getLeccion().getId(), usuarioId);
            leccionesCompletadas.put(leccionId, completado);
        }

        boolean desbloqueado = true;
        Map<Long, Boolean> leccionesDesbloqueadas = new TreeMap<>();

        for (Map.Entry<Long, Boolean> entry : leccionesCompletadas.entrySet()) {
            Long leccionId = entry.getKey();
            boolean completado = entry.getValue();

            leccionesDesbloqueadas.put(leccionId, desbloqueado);
            desbloqueado = completado;
        }

        modelo.put("progresos", leccionesDesbloqueadas);
        return new ModelAndView("mapa-braille-traduccion", modelo);
    }
}
