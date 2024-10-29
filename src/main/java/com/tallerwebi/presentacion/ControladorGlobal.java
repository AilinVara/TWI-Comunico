package com.tallerwebi.presentacion;

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

    @Autowired
    public ControladorGlobal(ServicioUsuario servicioUsuario){
        this.servicioUsuario = servicioUsuario;
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
}
