package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioSolicitudAmistad;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ControladorSolicitudAmistadTest {

        private ControladorSolicitudAmistad controladorSolicitudAmistad;
        private ServicioUsuario servicioUsuarioMock;
        private ServicioSolicitudAmistad servicioSolicitudAmistadMock;
        private HttpServletRequest requestMock;
        private HttpSession sessionMock;
        private Usuario usuarioMock;
        private RedirectAttributes redirectAttributesMock;

        @BeforeEach
        public void init() {
            servicioUsuarioMock = mock(ServicioUsuario.class);
            servicioSolicitudAmistadMock = mock(ServicioSolicitudAmistad.class);
            requestMock = mock(HttpServletRequest.class);
            sessionMock = mock(HttpSession.class);
            usuarioMock = mock(Usuario.class);
            redirectAttributesMock = mock(RedirectAttributes.class);
            controladorSolicitudAmistad = new ControladorSolicitudAmistad(servicioUsuarioMock, servicioSolicitudAmistadMock);

            when(requestMock.getSession()).thenReturn(sessionMock);
            when(sessionMock.getAttribute("id")).thenReturn(1L);

        }

//        @Test
//        public void enviarSolicitudTest() {
//            Usuario solicitadoMock = mock(Usuario.class);
//            Long idAmigo = 2L;
//
//            when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuarioMock);
//            when(servicioSolicitudAmistadMock.comprobarSolicitudPendiente(solicitadoMock, usuarioMock)).thenReturn(false);
//
//            ModelAndView modelAndView = controladorSolicitudAmistad.enviarSolicitud(requestMock, idAmigo);
//
//            verify(servicioSolicitudAmistadMock, times(1)).enviarSolicitud(usuarioMock, solicitadoMock);
//            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/mostrarAmigos"));
//        }
//
//        @Test
//        public void aceptarSolicitudTest() {
//            Long idSolicitud = 2L;
//
//            ModelAndView modelAndView = controladorSolicitudAmistad.aceptarNotificacion(requestMock, idSolicitud);
//
//            verify(servicioSolicitudAmistadMock, times(1)).aceptarSolicitud(idSolicitud);
//            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/solicitud-amistad"));
//        }
//
//        @Test
//        public void rechazarNotificacionTest() {
//            Long idSolicitud = 2L;
//
//            ModelAndView modelAndView = controladorSolicitudAmistad.rechazarNotificacion(requestMock, idSolicitud);
//
//            verify(servicioSolicitudAmistadMock, times(1)).rechazarSolicitud(idSolicitud);
//            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/solicitud-amistad"));
//        }

        @Test
        public void irASolicitudesRedirectToLoginWhenUserIsNullTest() {
            when(sessionMock.getAttribute("id")).thenReturn(1L);

            ModelAndView modelAndView = controladorSolicitudAmistad.irANotificaciones(requestMock);

            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/inicio"));
        }

        @Test
        public void enviarSolicitudRedirectToLoginWhenUserIsNullTest() {
            when(sessionMock.getAttribute("id")).thenReturn(1L);
            Long idAmigo = 1L;

            ModelAndView modelAndView = controladorSolicitudAmistad.enviarSolicitud(requestMock, idAmigo);

            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/inicio"));
        }

        @Test
        public void aceptarSolicitudRedirectToLoginWhenUserIsNullTest() {
            when(sessionMock.getAttribute("id")).thenReturn(1L);

            Long idNotificacion = 1L;

            ModelAndView modelAndView = controladorSolicitudAmistad.aceptarNotificacion(requestMock, idNotificacion);

            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/inicio"));
        }

        @Test
        public void rechazarSolicitudRedirectToLoginWhenUserIsNullTest() {
            when(sessionMock.getAttribute("id")).thenReturn(1L);
            Long idNotificacion = 1L;

            ModelAndView modelAndView = controladorSolicitudAmistad.rechazarNotificacion(requestMock, idNotificacion);

            assertThat(modelAndView.getViewName(), equalToIgnoringCase("redirect:/inicio"));
        }

    }
