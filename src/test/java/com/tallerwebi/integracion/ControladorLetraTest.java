package com.tallerwebi.integracion;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.ServicioLetra;
import com.tallerwebi.presentacion.ControladorLetra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
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
    public void testBuscarLetraPorNombre() {
        // Crear una letra de ejemplo
        Letra letra = new Letra(1L, "A", "ruta/a/imagen.png");

        // Definir comportamiento del mock
        when(servicioLetraMock.buscarPorNombre("A")).thenReturn((Letra) List.of(letra));

        // Llamar al controlador
        ModelAndView modelAndView = controladorLetra.buscarLetraPorNombre("A");

        // Obtener el modelo del ModelAndView
        ModelMap modelo = modelAndView.getModelMap();
        List<Letra> resultado = (List<Letra>) modelo.get("letras");

        // Verificar el resultado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("A", resultado.get(0).getNombre());

    }
}
