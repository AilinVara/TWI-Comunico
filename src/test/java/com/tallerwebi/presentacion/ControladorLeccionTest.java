package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ProgresoLeccion;
import com.tallerwebi.dominio.ServicioLeccion;
import com.tallerwebi.dominio.ServicioProgresoLeccion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorLeccionTest {

    private ServicioProgresoLeccion servicioProgresoMock;
    private ServicioLeccion servicioLeccionMock;
    private ControladorLeccion controladorLeccion;

    @BeforeEach
    public void init(){
        servicioProgresoMock = mock(ServicioProgresoLeccion.class);
        servicioLeccionMock = mock(ServicioLeccion.class);
        controladorLeccion = new ControladorLeccion(servicioLeccionMock,servicioProgresoMock);
    }

    @Test
    public void dadoElIdDeUnaLeccionCuandoSeLlamaAlMetodoLeccionEntoncesRedirigeAlPrimerEjercicio() {
        Long leccionId = 1L;
        Leccion leccion = new Leccion();
        leccion.setId(leccionId);

        when(servicioLeccionMock.obtenerLeccion(leccionId)).thenReturn(leccion);

        ModelAndView modelAndView = controladorLeccion.leccion(leccionId);

        assertEquals("redirect:/ejercicio/1?leccion=" + leccionId, modelAndView.getViewName());
    }

    @Test
    public void dadoQueUnUsuarioLlamaALeccionesTraduccionEntoncesDevuelveLaVistaConSuProgresoYLasLeccionesConSuEstadoDeDesbloqueo() {
        Long usuarioId = 1L;
        List<ProgresoLeccion> progresosTraduccion = new ArrayList<>();

        ProgresoLeccion progreso1 = new ProgresoLeccion();
        progreso1.setLeccion(new Leccion());
        progreso1.getLeccion().setId(1L);

        ProgresoLeccion progreso2 = new ProgresoLeccion();
        progreso2.setLeccion(new Leccion());
        progreso2.getLeccion().setId(2L);

        ProgresoLeccion progreso3 = new ProgresoLeccion();
        progreso3.setLeccion(new Leccion());
        progreso3.getLeccion().setId(3L);

        progresosTraduccion.add(progreso1);
        progresosTraduccion.add(progreso2);
        progresosTraduccion.add(progreso3);

        when(servicioProgresoMock.buscarProgresoPorTipoEjercicio("traduccion", usuarioId)).thenReturn(progresosTraduccion);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(1L, usuarioId)).thenReturn(true);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(2L, usuarioId)).thenReturn(false);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(3L, usuarioId)).thenReturn(false);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(usuarioId);

        ModelAndView modelAndView = controladorLeccion.leccionesTraduccion(request);

        Map<Long, Boolean> progresos = (Map<Long, Boolean>) modelAndView.getModel().get("progresos");

        assertThat(modelAndView.getViewName(), equalTo("mapa-braille-traduccion"));
        assertThat(progresos, is(notNullValue()));
        assertThat(progresos, hasEntry(1L, true));
        assertThat(progresos, hasEntry(2L, true));
        assertThat(progresos, hasEntry(3L, false));
    }

    @Test
    public void dadoQueUnUsuarioLlamaALeccionesTraduccionEntoncesDevuelveLaVistaConSuProgresoYLasLeccionesBloqueadas() {
        Long usuarioId = 1L;
        List<ProgresoLeccion> progresosTraduccion = new ArrayList<>();

        ProgresoLeccion progreso1 = new ProgresoLeccion();
        progreso1.setLeccion(new Leccion());
        progreso1.getLeccion().setId(1L);

        ProgresoLeccion progreso2 = new ProgresoLeccion();
        progreso2.setLeccion(new Leccion());
        progreso2.getLeccion().setId(2L);

        ProgresoLeccion progreso3 = new ProgresoLeccion();
        progreso3.setLeccion(new Leccion());
        progreso3.getLeccion().setId(3L);

        progresosTraduccion.add(progreso1);
        progresosTraduccion.add(progreso2);
        progresosTraduccion.add(progreso3);

        when(servicioProgresoMock.buscarProgresoPorTipoEjercicio("traduccion", usuarioId)).thenReturn(progresosTraduccion);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(1L, usuarioId)).thenReturn(false);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(2L, usuarioId)).thenReturn(false);
        when(servicioProgresoMock.verificarCompletadoPorLeccion(3L, usuarioId)).thenReturn(false);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("id")).thenReturn(usuarioId);

        ModelAndView modelAndView = controladorLeccion.leccionesTraduccion(request);

        Map<Long, Boolean> progresos = (Map<Long, Boolean>) modelAndView.getModel().get("progresos");

        assertThat(modelAndView.getViewName(), equalTo("mapa-braille-traduccion"));
        assertThat(progresos, is(notNullValue()));
        assertThat(progresos, hasEntry(1L, true));
        assertThat(progresos, hasEntry(2L, false));
        assertThat(progresos, hasEntry(3L, false));
    }



}
