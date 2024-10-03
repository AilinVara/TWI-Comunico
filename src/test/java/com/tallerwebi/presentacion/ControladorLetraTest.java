package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.ServicioLetra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ControladorLetraTest {

    private ControladorLetra controladorLetra;
    private ServicioLetra servicioLetraMock;

    @BeforeEach
    public void init() {
        // Crear el mock del servicio
        servicioLetraMock = mock(ServicioLetra.class);
        controladorLetra = new ControladorLetra(servicioLetraMock);
    }

    @Test
    public void cuandoNavegoASeniasAlfabetoEntoncesReciboLaVistaAlfabetoYUnaListaConLetrasEnElModelo(){
        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");

        when(servicioLetraMock.buscarTodasLasLetras()).thenReturn(List.of(letra));

        ModelAndView modelAndView = this.controladorLetra.alfabetoSenias("");

        assertNotNull(modelAndView);
        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
        assertThat(modelAndView.getModel().size(), equalTo(1) );
        assertThat(modelAndView.getModel().get("letrassenias"), equalTo(List.of(letra)));
    }

    @Test
    public void cuandoNavegoABrailleAlfabetoEntoncesReciboLaVistaAlfabetoYUnaListaConLetrasEnElModelo(){
        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");

        when(servicioLetraMock.buscarTodasLasLetras()).thenReturn(List.of(letra));

        ModelAndView modelAndView = this.controladorLetra.alfabetoBraille("");

        assertNotNull(modelAndView);
        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
        assertThat(modelAndView.getModel().size(), equalTo(1) );
        assertThat(modelAndView.getModel().get("letrasbraille"), equalTo(List.of(letra)));
    }

    @Test
    public void cuandoNavegoASeniasAlfabetoYEnvioUnaLetraDeParametroEntoncesReciboLaVistaAlfabetoYLaLetraEnElModelo() {
        // Crear una letra de ejemplo
        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");

        // Definir comportamiento del mock
        when(servicioLetraMock.buscarPorNombre("A")).thenReturn(letra);

        // Llamar al controlador
        ModelAndView modelAndView = controladorLetra.alfabetoSenias("A");

        // Verificar el resultado
        assertNotNull(modelAndView);
        assertThat(modelAndView.getModel().size(), equalTo(1) );
        assertThat(modelAndView.getModel().get("letrassenias"), equalTo(List.of(letra)));
        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
    }

    @Test
    public void cuandoNavegoABrailleAlfabetoYEnvioUnaLetraDeParametroEntoncesReciboLaVistaAlfabetoYLaLetraEnElModelo() {
        // Crear una letra de ejemplo
        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");

        // Definir comportamiento del mock
        when(servicioLetraMock.buscarPorNombre("A")).thenReturn(letra);

        // Llamar al controlador
        ModelAndView modelAndView = controladorLetra.alfabetoBraille("A");

        // Verificar el resultado
        assertNotNull(modelAndView);
        assertThat(modelAndView.getModel().size(), equalTo(1));
        assertThat(modelAndView.getModel().get("letrasbraille"), equalTo(List.of(letra)));
        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
    }
}
