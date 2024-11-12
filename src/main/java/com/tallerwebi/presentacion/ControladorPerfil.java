package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioPerfilUsuario;
import com.tallerwebi.dominio.ServicioSuscripcion;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorPerfil {

        private ServicioPerfilUsuario servicioPerfilUsuario;
        private ServicioSuscripcion servicioSuscripcion;
    private RepositorioUsuario servicioUsuario;


    @Autowired
        public ControladorPerfil(ServicioPerfilUsuario servicioPerfilUsuario, ServicioSuscripcion servicioSuscripcion, RepositorioUsuario servicioUsuario) {
            this.servicioPerfilUsuario = servicioPerfilUsuario;
            this.servicioSuscripcion = servicioSuscripcion;
        this.servicioUsuario = servicioUsuario;
    }

        @GetMapping("/perfil")
        public ModelAndView mostrarPerfil(HttpServletRequest request, @ModelAttribute("amigo") Usuario amigo, @ModelAttribute("modal") String modal) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

            if (usuario != null) {
                ModelMap model = new ModelMap();
                model.put("usuario", usuario);
                model.put("historialDeCompras", servicioPerfilUsuario.historialDeCompras(usuario));
                model.put("amigosUsuario", servicioPerfilUsuario.buscarAmigos(usuario));
                model.addAttribute("sin plan", servicioSuscripcion.descripcionSuscripciones(1L));
                model.addAttribute("basico", servicioSuscripcion.descripcionSuscripciones(2L));
                model.addAttribute("estandar", servicioSuscripcion.descripcionSuscripciones(3L));
                model.addAttribute("premium", servicioSuscripcion.descripcionSuscripciones(4L));
                if (amigo != null) {
                    model.put("amigo", amigo);
                    model.put("modal", modal);
                }

                return new ModelAndView("perfilUsuario", model);
            }
            return new ModelAndView("redirect:/login");
        }


        @PostMapping("/perfil/editarPerfilCompleto")
        public String editarPerfilCompleto(@RequestParam(name = "file", required = false) MultipartFile foto,
                                           @ModelAttribute("usuario") Usuario usuario,
                                           RedirectAttributes flash,
                                           HttpServletRequest request) {

            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuarioExistente = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
            if (usuarioExistente == null) {
                // Manejar el caso donde el usuario no se encuentra
                flash.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/perfilUsuario";
            }


            try {
                servicioPerfilUsuario.editarPerfilCompleto(usuarioExistente, usuario, foto);
                flash.addFlashAttribute("success", "Usuario modificado");
            } catch (Exception e) {
                flash.addFlashAttribute("error", "Error al guardar la foto: " + e.getMessage());
                return "redirect:/login";
            }


            return "redirect:/perfilUsuario";
        }
    }