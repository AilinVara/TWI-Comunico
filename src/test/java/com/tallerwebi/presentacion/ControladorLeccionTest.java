//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class ControladorLeccionTest {
//
//    private ServicioProgresoLeccion servicioProgresoMock;
//    private ServicioLeccion servicioLeccionMock;
//    private ServicioVida servicioVidaMock;
//    private ControladorLeccion controladorLeccion;
//
//    @BeforeEach
//    public void init() {
//        servicioProgresoMock = mock(ServicioProgresoLeccion.class);
//        servicioLeccionMock = mock(ServicioLeccion.class);
//        servicioVidaMock = mock(ServicioVida.class);
//        controladorLeccion = new ControladorLeccion(servicioLeccionMock, servicioProgresoMock, servicioVidaMock);
//    }
//
//    @Test
//    public void dadoElIdDeUnaLeccionCuandoSeLlamaAlMetodoLeccionEntoncesRedirigeAlPrimerEjercicio() {
//        Long leccionId = 1L;
//        Leccion leccion = new Leccion();
//        leccion.setId(leccionId);
//
//        when(servicioLeccionMock.obtenerLeccion(leccionId)).thenReturn(leccion);
//
//        ModelAndView modelAndView = controladorLeccion.leccion(leccionId);
//
//        assertEquals("redirect:/ejercicio/1?leccion=" + leccionId, modelAndView.getViewName());
//    }
//
//    @Test
//    public void dadoQueUnUsuarioLlamaALeccionesTraduccionEntoncesDevuelveLaVistaConSuProgresoYLasLeccionesConSuEstadoDeDesbloqueo() {
//        Long usuarioId = 1L;
//        Map<Long, Boolean> leccionesDesbloqueadas = new HashMap<>();
//        leccionesDesbloqueadas.put(1L, true);
//        leccionesDesbloqueadas.put(2L, false);
//        leccionesDesbloqueadas.put(3L, false);
//
//        when(servicioProgresoMock.buscarProgresoPorTipoEjercicioConEstado("traduccion", usuarioId)).thenReturn(leccionesDesbloqueadas);
//        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(new Vida());
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("id")).thenReturn(usuarioId);
//
//        ModelAndView modelAndView = controladorLeccion.obtenerLecciones("traduccion", request);
//
//        Map<Long, Boolean> progresos = (Map<Long, Boolean>) modelAndView.getModel().get("progresos");
//
//        assertThat(modelAndView.getViewName(), equalTo("mapa-lecciones"));
//        assertThat(progresos, is(notNullValue()));
//        assertThat(progresos, hasEntry(1L, true));
//        assertThat(progresos, hasEntry(2L, true));
//        assertThat(progresos, hasEntry(3L, false));
//    }
//
//    @Test
//    public void dadoQueUnUsuarioLlamaALeccionesTraduccionEntoncesDevuelveLaVistaConSuProgresoYLasLeccionesBloqueadas() {
//        Long usuarioId = 1L;
//        Map<Long, Boolean> leccionesDesbloqueadas = new HashMap<>();
//        leccionesDesbloqueadas.put(1L, false);
//        leccionesDesbloqueadas.put(2L, false);
//        leccionesDesbloqueadas.put(3L, false);
//
//        when(servicioProgresoMock.buscarProgresoPorTipoEjercicioConEstado("traduccion", usuarioId)).thenReturn(leccionesDesbloqueadas);
//        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(new Vida());
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("id")).thenReturn(usuarioId);
//
//        ModelAndView modelAndView = controladorLeccion.obtenerLecciones("traduccion", request);
//
//        Map<Long, Boolean> progresos = (Map<Long, Boolean>) modelAndView.getModel().get("progresos");
//
//        assertThat(modelAndView.getViewName(), equalTo("mapa-lecciones"));
//        assertThat(progresos, is(notNullValue()));
//        assertThat(progresos, hasEntry(1L, true));
//        assertThat(progresos, hasEntry(2L, false));
//        assertThat(progresos, hasEntry(3L, false));
//    }
//
//    @Test
//    public void dadoQueUnUsuarioLlamaALeccionesTraduccionYNoTieneVidasEntoncesSeImpideContinuar() {
//        Long leccionId = 1L;
//        Long usuarioId = 1L;
//        Leccion leccion = new Leccion();
//        leccion.setId(leccionId);
//
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpSession session = mock(HttpSession.class);
//        when(request.getSession()).thenReturn(session);
//        when(session.getAttribute("id")).thenReturn(usuarioId);
//
//        Vida sinVidas = new Vida();
//        sinVidas.setCantidadDeVidasActuales(0);
//
//        when(servicioLeccionMock.obtenerLeccion(leccionId)).thenReturn(leccion);
//        when(servicioVidaMock.obtenerVida(usuarioId)).thenReturn(sinVidas);
//
//        ModelAndView modelAndView = controladorLeccion.obtenerLecciones("traduccion", request);
//
//        assertEquals("mapa-lecciones", modelAndView.getViewName());
//    }
//
//}
