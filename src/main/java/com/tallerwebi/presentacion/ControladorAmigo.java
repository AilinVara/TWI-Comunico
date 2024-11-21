package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfilUsuario;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorAmigo {

        private ServicioUsuario servicioUsuario;
        private ServicioPerfilUsuario servicioPerfilUsuario;

        @Autowired
        public ControladorAmigo(ServicioUsuario servicioUsuario, ServicioPerfilUsuario servicioPerfilUsuario) {
            this.servicioUsuario = servicioUsuario;
            this.servicioPerfilUsuario = servicioPerfilUsuario;
        }

        @GetMapping("/mostrarAmigos")
        public ModelAndView mostrarAmigos(HttpServletRequest request) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

                if (usuario != null) {
            ModelMap model = new ModelMap();
            model.put("amigosUsuario", servicioPerfilUsuario.buscarAmigos(usuario));
//                if (amigo != null) {
//                    model.put("amigo", amigo);
//                    model.put("modal", modal);
//                }
                return new ModelAndView("amigos", model);
                }

            return new ModelAndView("redirect:/indice");
        }

//        @GetMapping("/mostrarAmigos")
//        public ModelAndView mostrarAmigos(HttpServletRequest request) {
//            HttpSession session = request.getSession();
//            Long idUsuario = (Long) session.getAttribute("id");
//            Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
//            if (usuario != null) {
//                ModelMap model = new ModelMap();
//                model.put("usuarios", servicioUsuario.listar(usuario));
//                return new ModelAndView("amigos",model);
//            }
//            return new ModelAndView("redirect:/indice");
//        }

    @RequestMapping(value = "/buscarAmigos", method = RequestMethod.POST)
    public ModelAndView buscarAmigos(HttpServletRequest request, @RequestParam(required = false) String nombre) {
            HttpSession session = request.getSession();
            List<Usuario> usuariosEncontrados = this.servicioUsuario.buscarUsuariosPorNombre(nombre);
        if (usuariosEncontrados != null) {
            ModelMap model = new ModelMap();
            model.put("usuarios", usuariosEncontrados);
            return new ModelAndView("amigos",model);
        }
        return new ModelAndView("redirect:/indice");
    }

}
