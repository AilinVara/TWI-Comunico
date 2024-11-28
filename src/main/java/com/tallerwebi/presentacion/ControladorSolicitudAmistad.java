package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller

public class ControladorSolicitudAmistad {

    private ServicioUsuario servicioUsuario;
    private ServicioSolicitudAmistad servicioSolicitudAmistad;

        @Autowired
        public ControladorSolicitudAmistad(ServicioUsuario servicioUsuario, ServicioSolicitudAmistad servicioSolicitudAmistad) {
            this.servicioUsuario = servicioUsuario;
            this.servicioSolicitudAmistad = servicioSolicitudAmistad;
        }

        @RequestMapping("/solicitud-amistad")
        public ModelAndView irANotificaciones(HttpServletRequest request) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);
            ModelMap modelo = new ModelMap();

            List<SolicitudAmistad> solicitudesDeAmistad = new ArrayList<>(servicioSolicitudAmistad.buscarSolicitudes(usuario));

            List<Object> listaSolicitudes = new ArrayList<>();

            if(usuario != null){
                listaSolicitudes.addAll(solicitudesDeAmistad);

                listaSolicitudes.sort((o1, o2) -> {
                    LocalDateTime fecha1 = null;
                    LocalDateTime fecha2 = null;

                    if (o1 instanceof SolicitudAmistad) {
                        fecha1 = ((SolicitudAmistad) o1).getFecha();
                    }

                    if (o2 instanceof SolicitudAmistad) {
                        fecha2 = ((SolicitudAmistad) o2).getFecha();
                    }

                    return fecha1.compareTo(fecha2);
                });

                modelo.put("datosLogin", new DatosLogin());

                modelo.put("solicitud", listaSolicitudes);
                return new ModelAndView("solicitudes", modelo);
            }
            return new ModelAndView("redirect:/inicio");
        }

        @PostMapping("/enviar-solicitud")
        public ModelAndView enviarSolicitud(HttpServletRequest request, @RequestParam("idAmigo") Long idAmigo) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario solicitante = servicioUsuario.buscarUsuarioPorId(idUsuario);

            if (solicitante != null) {
                Usuario solicitado = servicioUsuario.buscarUsuarioPorId(idAmigo);
                if (solicitado != null && !servicioSolicitudAmistad.comprobarSolicitudPendiente(solicitado, solicitante)) {
                    servicioSolicitudAmistad.enviarSolicitud(solicitante, solicitado);
                    return new ModelAndView("redirect:/mostrarAmigos");
                }
            }
            return new ModelAndView("redirect:/inicio");
        }

        @PostMapping("/aceptar-notificacion")
        public ModelAndView aceptarNotificacion(HttpServletRequest request, @RequestParam("notificacion") Long idNotificacion) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario aceptante = servicioUsuario.buscarUsuarioPorId(idUsuario);

            if (aceptante != null) {

                servicioSolicitudAmistad.aceptarSolicitud(idNotificacion);

                return new ModelAndView("redirect:/mostrarAmigos");
            }

            return new ModelAndView("redirect:/inicio");
        }

        @PostMapping("/rechazar-notificacion")
        public ModelAndView rechazarNotificacion(HttpServletRequest request, @RequestParam("notificacion") Long idNotificacion) {
            HttpSession session = request.getSession();
            Long idUsuario = (Long) session.getAttribute("id");
            Usuario aceptante = servicioUsuario.buscarUsuarioPorId(idUsuario);

            if (aceptante != null) {

                servicioSolicitudAmistad.rechazarSolicitud(idNotificacion);

                return new ModelAndView("redirect:/solicitud-amistad");
            }

            return new ModelAndView("redirect:/inicio");
        }
}