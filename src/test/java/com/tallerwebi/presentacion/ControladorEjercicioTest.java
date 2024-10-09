package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.Matriz;
import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.ServicioEjercicio;
import com.tallerwebi.dominio.ServicioMatriz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEjercicioTest {

    private ControladorEjercicio controladorEjercicio;
    private Ejercicio ejercicioMock;
    private ServicioEjercicio servicioEjercicioMock;
    private ServicioMatriz servicioMatrizMock;
    private Matriz matrizMock; 

    @BeforeEach
    public void init(){
        ejercicioMock = mock(Ejercicio.class);
        when(ejercicioMock.getId()).thenReturn(1L);

        Opcion opcionCorrectaMock = mock(Opcion.class);
        when(opcionCorrectaMock.getId()).thenReturn(1L);

        Opcion opcionIncorrectaMock = mock(Opcion.class);
        when(opcionIncorrectaMock.getId()).thenReturn(2L);
        List<Opcion> opcionesIncorrectas = List.of(opcionIncorrectaMock);

        when(ejercicioMock.getOpcionCorrecta()).thenReturn(opcionCorrectaMock);
        when(ejercicioMock.getOpcionesIncorrectas()).thenReturn(opcionesIncorrectas);

        servicioEjercicioMock = mock(ServicioEjercicio.class);
        when(servicioEjercicioMock.obtenerEjercicio(anyLong())).thenReturn(ejercicioMock);

        servicioMatrizMock = mock(ServicioMatriz.class);
        matrizMock = mock(Matriz.class);
        when(servicioMatrizMock.obtenerMatriz(anyLong())).thenReturn(matrizMock);

        controladorEjercicio = new ControladorEjercicio(servicioEjercicioMock, servicioMatrizMock);
    }

    @Test
    public void cuandoVoyAEjercicioReciboLaVistaEjercicioYUnObjetoEjercicioEnElModelo(){
        ModelAndView modelAndView = controladorEjercicio.irAEjercicio(anyLong());

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("ejercicio"), equalTo(ejercicioMock));
    }

    @Test
    public void contestarConLaRespuestaCorrectaDebeRetornarEsCorrectaComoTrue(){
        Long opcionCorrecta = ejercicioMock.getOpcionCorrecta().getId();

        when(servicioEjercicioMock.resolverEjercicio(ejercicioMock, opcionCorrecta)).thenReturn(true);

        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(opcionCorrecta, ejercicioMock.getId());

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(true));
    }

    @Test
    public void contestarConLaRespuestaIncorrectaDebeRetornarEsCorrectaComoFalse(){
        Opcion opcionIncorrecta = ejercicioMock.getOpcionesIncorrectas().get(0);

        when(servicioEjercicioMock.resolverEjercicio(ejercicioMock, opcionIncorrecta.getId())).thenReturn(false);

        ModelAndView modelAndView = controladorEjercicio.resolverEjercicio(opcionIncorrecta.getId(), ejercicioMock.getId());

        assertThat(modelAndView.getViewName(), equalToIgnoringCase("ejercicio"));
        assertThat(modelAndView.getModel().get("esCorrecta"), is(false));
    }

}
