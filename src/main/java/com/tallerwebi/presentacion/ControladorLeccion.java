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
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorLeccion(ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida, ServicioUsuario servicioUsuario) {
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
        this.servicioUsuario = servicioUsuario;
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
        Map<Long, String> enlaces = new LinkedHashMap<>();

        for (Map.Entry<Long, Boolean> entry : leccionesDesbloqueadas.entrySet()) {
            Long leccionId = entry.getKey();
            boolean completado = entry.getValue();

            leccionesDesbloqueadas.put(leccionId, desbloqueado);
            desbloqueado = completado;

            String enlace = "/leccion/" + leccionId;
            if ("combinado".equals(tipo)) {
                enlace += "?combinado=true";
            }
            enlaces.put(leccionId, enlace);
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
            case "combinado":
                tipo = "Ejercicios combinados";
                break;
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        String suscripcion = usuario.getSuscripcion().getTipoSuscripcion().getNombre();


        modelo.put("vidas", vidas);
        modelo.put("tipo", tipo);
        modelo.put("progresos", leccionesDesbloqueadas);
        modelo.put("enlaces", enlaces);
        modelo.put("suscripcion", suscripcion);

        return new ModelAndView("mapa-lecciones", modelo);
    }

    @RequestMapping("/senias/lecciones/reconoce-gestos")
    public ModelAndView obtenerLeccionesSenias(HttpServletRequest request){
        ModelMap modelo = new ModelMap();
        String tipo = "senia";
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
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        String suscripcion = usuario.getSuscripcion().getTipoSuscripcion().getNombre();

        tipo = "Reconocé gestos";

        modelo.put("vidas", vidas);
        modelo.put("tipo", tipo);
        modelo.put("progresos", leccionesDesbloqueadas);
        modelo.put("suscripcion", suscripcion);

        return new ModelAndView("mapa-lecciones", modelo);
    }


    @RequestMapping("/desafio-velocidad")
    public ModelAndView desafioVelocidad(HttpServletRequest request) {
        return new ModelAndView("desafio-velocidad");
    }

}
