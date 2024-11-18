package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorAmigo {

        private ServicioUsuario servicioUsuario;

        @Autowired
        public ControladorAmigo(ServicioUsuario servicioUsuario) {
            this.servicioUsuario = servicioUsuario;
        }

        @GetMapping("/mostrarAmigos")
        public ModelAndView mostrarAmigos(HttpServletRequest request) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
            if (usuario != null) {
                ModelMap model = new ModelMap();
                model.put("usuarios", servicioUsuario.listar(usuario));
                return new ModelAndView("amigos",model);
            }
            return new ModelAndView("redirect:/indice");
        }

}
