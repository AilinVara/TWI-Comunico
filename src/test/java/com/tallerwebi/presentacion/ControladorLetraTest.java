package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.ServicioLetra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

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
        assertEquals("alfabeto",modelAndView.getViewName());
        assertEquals(1, modelAndView.getModel().size());
        assertEquals(List.of(letra), modelAndView.getModel().get("letrassenias"));
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
        assertEquals(1, modelAndView.getModel().size());
        assertEquals(List.of(letra), modelAndView.getModel().get("letrassenias"));
        assertEquals("alfabeto", modelAndView.getViewName());
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
        assertEquals(1, modelAndView.getModel().size());
        assertEquals(List.of(letra), modelAndView.getModel().get("letrasbraille"));
        assertEquals("alfabeto", modelAndView.getViewName());
    }
}
