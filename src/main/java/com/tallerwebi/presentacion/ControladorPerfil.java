package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
import java.util.*;

@Controller
public class ControladorPerfil {

        private ServicioPerfilUsuario servicioPerfilUsuario;
        private ServicioSuscripcion servicioSuscripcion;
        private RepositorioUsuario repositorioUsuario;

        //para obtener el historial de lecciones
        private ServicioProgresoLeccion servicioProgresoLeccion;


    @Autowired
        public ControladorPerfil(ServicioPerfilUsuario servicioPerfilUsuario, ServicioSuscripcion servicioSuscripcion, RepositorioUsuario servicioUsuario,
                                 ServicioProgresoLeccion servicioProgresoLeccion) {
            this.servicioPerfilUsuario = servicioPerfilUsuario;
            this.servicioSuscripcion = servicioSuscripcion;
            this.repositorioUsuario = servicioUsuario;
            this.servicioProgresoLeccion = servicioProgresoLeccion;
    }

        @GetMapping("/perfil")
        public ModelAndView mostrarPerfil(HttpServletRequest request, @ModelAttribute("amigo") Usuario amigo, @ModelAttribute("modal") String modal) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuario = this.repositorioUsuario.buscarUsuarioPorId(idUsuario);
            List<Leccion> lecciones = servicioProgresoLeccion.obtenerLecciones();

            if (usuario != null) {
                ModelMap model = new ModelMap();
                model.put("usuario", usuario);
                model.put("historialDeCompras", servicioPerfilUsuario.historialDeCompras(usuario));
                model.put("amigosUsuario", servicioPerfilUsuario.buscarAmigos(usuario));

                List<ProgresoLeccion> listaProgresos = new ArrayList<>();
                Set<Long> filtroLecciones = new HashSet<>();
                Long leccionNum = 0L;

                for (Leccion leccion : lecciones) {
                    Map<String, String> nombreLecciones = leccion.generarNombreTipoLeccion();

                    if (this.servicioProgresoLeccion.verificarCompletadoPorLeccion(leccion.getId(), idUsuario)) {
                        List<ProgresoLeccion> progresos = this.servicioProgresoLeccion.buscarProgresoLeccionDeUsuario(idUsuario, leccion.getId());
                     if(!filtroLecciones.contains(leccion.getId())) {
                         if(!progresos.isEmpty()){
                             leccionNum++;
                             String tipo = progresos.get(0).getLeccion().getTipo();
                             progresos.get(0).getLeccion().setTipo(nombreLecciones.getOrDefault(tipo, tipo));
                             progresos.get(0).getLeccion().setId(leccionNum);
                             listaProgresos.add(progresos.get(0));
                             filtroLecciones.add(leccion.getId());
                         }
                       }
                    }
                }
                model.put("progresos", listaProgresos);
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
            Usuario usuarioExistente = this.repositorioUsuario.buscarUsuarioPorId(idUsuario);
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