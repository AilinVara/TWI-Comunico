package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEjercicioTest {

    private ControladorEjercicio controladorEjercicio;
    private EjercicioTraduccion ejercicioTraduccionMock;
    private EjercicioMatriz ejercicioMatrizMock;
    private EjercicioFormaPalabra ejercicioFormaPalabraMock;
    private ServicioEjercicio servicioEjercicioMock;
    private ServicioLeccion servicioLeccionMock;
    private ServicioProgresoLeccion servicioProgresoLeccionMock;
    private HttpServletRequest requestMock;
    private Leccion leccionMock;
    private HttpSession sessionMock;
    private ProgresoLeccion progresoMock;
    private ServicioVida servicioVidaMock;
    private ServicioExperiencia servicioExperienciaMock;
    private ServicioUsuario servicioUsuarioMock;
    private Usuario usuarioMock;
    private ServicioTitulo servicioTituloMock;
    private TipoSuscripcion tipoSuscripcionMock;
    private Suscripcion suscripcionMock;

    @BeforeEach
    public void init() {
        ejercicioTraduccionMock = mock(EjercicioTraduccion.class);
        when(ejercicioTraduccionMock.getId()).thenReturn(1L);
        ejercicioMatrizMock = mock(EjercicioMatriz.class);
        when(ejercicioMatrizMock.getId()).thenReturn(1L);
        ejercicioFormaPalabraMock = mock(EjercicioFormaPalabra.class);
        when(ejercicioFormaPalabraMock.getId()).thenReturn(1L);

        requestMock = mock(HttpServletRequest.class);
        leccionMock = mock(Leccion.class);
        progresoMock = mock(ProgresoLeccion.class);
        sessionMock = mock(HttpSession.class);
        servicioUsuarioMock = mock(ServicioUsuario.class);

        usuarioMock = mock(Usuario.class);

        tipoSuscripcionMock = mock(TipoSuscripcion.class);
        when(tipoSuscripcionMock.getNombre()).thenReturn("sin plan");

        suscripcionMock = mock(Suscripcion.class);
        when(suscripcionMock.getTipoSuscripcion()).thenReturn(tipoSuscripcionMock);

        when(usuarioMock.getSuscripcion()).thenReturn(suscripcionMock);
        when(usuarioMock.getId()).thenReturn(1L);

        when(servicioUsuarioMock.buscarUsuarioPorId(anyLong())).thenReturn(usuarioMock);
        when(usuarioMock.getId()).thenReturn(1L);

        Opcion opcionCorrectaMock = mock(Opcion.class);
        when(opcionCorrectaMock.getId()).thenReturn(1L);

        Opcion opcionIncorrectaMock = mock(Opcion.class);
        when(opcionIncorrectaMock.getId()).thenReturn(2L);
        Set<Opcion> opcionesIncorrectas = new HashSet<>(Set.of(opcionIncorrectaMock));

        Vida vidaMock = mock(Vida.class);
        servicioVidaMock = mock(ServicioVida.class);
        when(vidaMock.getCantidadDeVidasActuales()).thenReturn(5);
        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(vidaMock);
        LocalDateTime haceUnosSegundos = LocalDateTime.now().minusSeconds(30);
        when(vidaMock.getUltimaVezQueSeRegeneroLaVida()).thenReturn(haceUnosSegundos);

        Experiencia experienciaMock = mock(Experiencia.class);
        servicioExperienciaMock = mock(ServicioExperiencia.class);

        servicioTituloMock = mock(ServicioTitulo.class);
        when(usuarioMock.getTitulo()).thenReturn("Novato");


        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vidaMock.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
        long tiempoRestante = 60 - (segundosDesdeUltimaRegeneracion % 60);

        when(ejercicioTraduccionMock.getOpcionCorrecta()).thenReturn(opcionCorrectaMock);
        when(ejercicioTraduccionMock.getOpcionesIncorrectas()).thenReturn(opcionesIncorrectas);

        servicioEjercicioMock = mock(ServicioEjercicio.class);
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioTraduccionMock);
        when(servicioEjercicioMock.resolverEjercicioTraduccion(ejercicioTraduccionMock, ejercicioTraduccionMock.getOpcionCorrecta().getId())).thenReturn(true);
        servicioLeccionMock = mock(ServicioLeccion.class);
        servicioProgresoLeccionMock = mock(ServicioProgresoLeccion.class);

        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        when(leccionMock.getTipo()).thenReturn("traduccion");

        controladorEjercicio = new ControladorEjercicio(servicioEjercicioMock, servicioLeccionMock, servicioProgresoLeccionMock, servicioVidaMock,servicioExperienciaMock, servicioTituloMock, servicioUsuarioMock);
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConEjerciciosTraduccionCuandoVoyAEjercicioReciboLaVistaEjercicioYUnEjercicioTraduccionEnElModelo(){
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioTraduccionMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);

        ModelAndView modelAndView = this.controladorEjercicio.irAjercicio(1L, 1,requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("ejercicio").getClass(), equalTo(ejercicioTraduccionMock.getClass()));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConEjerciciosMatrizCuandoVoyAEjercicioReciboLaVistaFormaLetraYUnEjercicioMatrizEnElModelo(){
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioMatrizMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);

        ModelAndView modelAndView = this.controladorEjercicio.irAjercicio(1L, 1,requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formaLetras"));
        assertThat(modelAndView.getModel().get("ejercicio").getClass(), equalTo(ejercicioMatrizMock.getClass()));
    }

    @Test
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConEjerciciosFormaPalabraCuandoVoyAEjercicioReciboLaVistaFormaPalabraYUnEjercicioFormaPalabraEnElModelo(){
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioFormaPalabraMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);

        ModelAndView modelAndView = this.controladorEjercicio.irAjercicio(1L, 1,requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicios-forma-palabra"));
        assertThat(modelAndView.getModel().get("ejercicio").getClass(), equalTo(ejercicioFormaPalabraMock.getClass()));
    }


    @Test
    public void contestarConLaRespuestaCorrectaDebeRetornarEsCorrectaComoTrue(){
        Long opcionCorrecta = ejercicioTraduccionMock.getOpcionCorrecta().getId();

        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioTraduccionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioProgresoLeccionMock.buscarPorIds(anyLong(), anyLong(), anyLong())).thenReturn(progresoMock);
        when(servicioEjercicioMock.resolverEjercicioTraduccion(ejercicioTraduccionMock, opcionCorrecta)).thenReturn(true);
        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(opcionCorrecta, "1", 1L, 1L, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(true));
    }

    @Test
    public void contestarConLaRespuestaIncorrectaDebeRetornarEsCorrectaComoFalse(){
        String opcionIncorrecta = ejercicioTraduccionMock.getOpcionesIncorrectas().iterator().next().getId().toString();


        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioTraduccionMock);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioProgresoLeccionMock.buscarPorIds(anyLong(), anyLong(), anyLong())).thenReturn(progresoMock);
        when(requestMock.getAttribute("nombreSuscripcion")).thenReturn("sin plan");
        when(servicioEjercicioMock.resolverEjercicioTraduccion(ejercicioTraduccionMock, Long.parseLong(opcionIncorrecta))).thenReturn(false);
        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(1L, opcionIncorrecta, 1L, 1L, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(false));
    }

    @Test
    public void usarAyudaEnEjercicioTraduccionDebeRetornarLaVistaEjercicioYDosOpcionesEnElModelo() {
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioTraduccionMock);
        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(mock(Vida.class));
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioTraduccionMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);
        when(servicioUsuarioMock.buscarUsuarioPorId(anyLong())).thenReturn(usuarioMock);
        when(usuarioMock.getAyudas()).thenReturn(4);

        ModelAndView modelAndView = controladorEjercicio.usarAyuda(1L, 1, requestMock);

        List<Opcion> opciones = (List<Opcion>) modelAndView.getModel().get("opciones");

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(opciones, is(notNullValue()));
        assertThat(opciones.size(), equalTo(2));
    }

    @Test
    public void usarAyudaEnEjercicioMatrizDebeRetornarLaVistaFormaLetrasYUnPuntoEnElModelo() {
        EjercicioMatriz ejercicioMatrizMock = mock(EjercicioMatriz.class);
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioMatrizMock);
        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(mock(Vida.class));
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioMatrizMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);
        when(servicioUsuarioMock.buscarUsuarioPorId(anyLong())).thenReturn(usuarioMock);
        when(usuarioMock.getAyudas()).thenReturn(4);
        when(ejercicioMatrizMock.getPuntos()).thenReturn("100100");

        ModelAndView modelAndView = controladorEjercicio.usarAyuda(1L, 1, requestMock);

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("formaLetras"));
        assertThat(modelAndView.getModel().get("punto"), is(notNullValue()));
    }

    @Test
    public void usarAyudaEnEjercicioFormaPalabraDebeRetornarLaVistaEjercicioFormaPalabraYUnaLetraMenosEnLasOpcionesDeLetras() {
        EjercicioFormaPalabra ejercicioPalabraMock = mock(EjercicioFormaPalabra.class);
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioPalabraMock);
        when(servicioVidaMock.obtenerVida(anyLong())).thenReturn(mock(Vida.class));
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute(anyString())).thenReturn(1L);
        when(servicioLeccionMock.obtenerLeccion(anyLong())).thenReturn(leccionMock);
        List<Ejercicio> lista = new ArrayList<>();
        lista.add(ejercicioPalabraMock);
        leccionMock.setEjercicios(lista);
        when(leccionMock.getEjercicios()).thenReturn(lista);
        when(servicioUsuarioMock.buscarUsuarioPorId(anyLong())).thenReturn(usuarioMock);
        when(usuarioMock.getAyudas()).thenReturn(4);
        when(ejercicioPalabraMock.getLetras()).thenReturn("P, R, U, E, B, A, Z");
        when(ejercicioPalabraMock.getRespuestaCorrecta()).thenReturn("PRUEBA");
        when(servicioEjercicioMock.convertirLetrasALista(ejercicioPalabraMock.getLetras())).thenReturn(obtenerLetrasALista());

        ModelAndView modelAndView = controladorEjercicio.usarAyuda(1L, 1, requestMock);

        List<String> letras = (List<String>) modelAndView.getModel().get("letras");

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicios-forma-palabra"));
        assertThat(letras.size(), equalTo(6));
    }

    private static List<String> obtenerLetrasALista() {
        List<String> letrasLista = new ArrayList<>();
        letrasLista.add("P");
        letrasLista.add("R");
        letrasLista.add("U");
        letrasLista.add("E");
        letrasLista.add("B");
        letrasLista.add("A");
        letrasLista.add("Z");
        return letrasLista;
    }

}
