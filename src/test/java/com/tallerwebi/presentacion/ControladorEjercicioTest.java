package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEjercicioTest {

    private ControladorEjercicio controladorEjercicio;
    private Ejercicio ejercicioMock;
    private ServicioEjercicio servicioEjercicioMock;
    private ServicioLeccion servicioLeccionMock;
    private ServicioProgresoLeccion servicioProgresoLeccionMock;
    private ServicioMatriz servicioMatrizMock;
    private HttpServletRequest requestMock;
    private Leccion leccionMock;
    private HttpSession sessionMock;
    private ProgresoLeccion progresoMock;
    private ServicioVida servicioVidaMock;

    @BeforeEach
    public void init() {
        ejercicioMock = mock(Ejercicio.class);
        when(ejercicioMock.getId()).thenReturn(1L);

        requestMock = mock(HttpServletRequest.class);
        leccionMock = mock(Leccion.class);
        progresoMock = mock(ProgresoLeccion.class);
        sessionMock = mock(HttpSession.class);

        Opcion opcionCorrectaMock = mock(Opcion.class);
        when(opcionCorrectaMock.getId()).thenReturn(1L);

        Opcion opcionIncorrectaMock = mock(Opcion.class);
        when(opcionIncorrectaMock.getId()).thenReturn(2L);
        List<Opcion> opcionesIncorrectas = List.of(opcionIncorrectaMock);

        Vida vidaMock = mock(Vida.class);
        servicioVidaMock = mock(ServicioVida.class);
        when(vidaMock.getCantidadDeVidasActuales()).thenReturn(5);
        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(vidaMock);
        LocalDateTime haceUnosSegundos = LocalDateTime.now().minusSeconds(30);
        when(vidaMock.getUltimaVezQueSeRegeneroLaVida()).thenReturn(haceUnosSegundos);

        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vidaMock.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
        long tiempoRestante = 60 - (segundosDesdeUltimaRegeneracion % 60);

        when(ejercicioMock.getOpcionCorrecta()).thenReturn(opcionCorrectaMock);
        when(ejercicioMock.getOpcionesIncorrectas()).thenReturn(opcionesIncorrectas);

        servicioEjercicioMock = mock(ServicioEjercicio.class);
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioMock);
        when(servicioEjercicioMock.resolverEjercicio(ejercicioMock, ejercicioMock.getOpcionCorrecta().getId())).thenReturn(true);
        servicioLeccionMock = mock(ServicioLeccion.class);
        servicioProgresoLeccionMock = mock(ServicioProgresoLeccion.class);
        servicioMatrizMock = mock(ServicioMatriz.class);
        controladorEjercicio = new ControladorEjercicio(servicioEjercicioMock, servicioLeccionMock, servicioProgresoLeccionMock, servicioMatrizMock, servicioVidaMock);
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConEjerciciosCuandoVoyAEjercicioReciboLaVistaEjercicioYUnObjetoEjercicioEnElModelo(){
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);

        ModelAndView modelAndView = this.controladorEjercicio.irAjercicio(1L, 1,requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("ejercicio"), equalTo(ejercicioMock));
    }


    @Test
    public void contestarConLaRespuestaCorrectaDebeRetornarEsCorrectaComoTrue(){
        Long opcionCorrecta = ejercicioMock.getOpcionCorrecta().getId();

        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioProgresoLeccionMock.buscarPorIds(anyLong(), anyLong(), anyLong())).thenReturn(progresoMock);
        when(servicioEjercicioMock.resolverEjercicio(ejercicioMock, opcionCorrecta)).thenReturn(true);
        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(opcionCorrecta, 1L, 1L, 1L, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(true));
    }

    @Test
    public void contestarConLaRespuestaIncorrectaDebeRetornarEsCorrectaComoFalse(){
        Long opcionIncorrecta = ejercicioMock.getOpcionesIncorrectas().get(0).getId();

        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioProgresoLeccionMock.buscarPorIds(anyLong(), anyLong(), anyLong())).thenReturn(progresoMock);
        when(servicioEjercicioMock.resolverEjercicio(ejercicioMock, opcionIncorrecta)).thenReturn(false);
        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(1L, opcionIncorrecta, 1L, 1L, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(false));
    }

}
