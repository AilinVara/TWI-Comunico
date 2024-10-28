package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExperiencia;
import com.tallerwebi.dominio.ServicioUsuario;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ControllerAdvice
public class ControladorGlobal {

    private final ServicioUsuario servicioUsuario;
    private final ServicioExperiencia servicioExperiencia;

    @Autowired
    public ControladorGlobal(ServicioUsuario servicioUsuario, ServicioExperiencia servicioExperiencia){
        this.servicioUsuario = servicioUsuario;
        this.servicioExperiencia = servicioExperiencia;
    }

    @ModelAttribute
    public void addAttributes(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Integer monedas = (Integer) session.getAttribute("points");
            if (monedas != null) {
                model.addAttribute("points", monedas);
            }
        }
    }
    @ModelAttribute("experiencia")
    public Integer obtenerExperienciaDelUsuario(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0; // Retorna un valor por defecto si no hay usuario logueado
        }
        return servicioExperiencia.obtenerExperiencia(usuarioId).getCantidadExperiencia();
    }
}
