package com.tallerwebi.dominio;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServicioLeccionImplTest {

    RepositorioLeccion repositorioLeccionMock;
    ServicioLeccion servicioLeccion;

    @BeforeEach
    public void init() {
        repositorioLeccionMock = mock(RepositorioLeccion.class);
        servicioLeccion = new ServicioLeccionImpl(repositorioLeccionMock);
    }

    @Test
    public void cuandoGuardoUnaLeccionEntoncesSeLlamaAlRepositorioGuardar() {
        Leccion leccion = new Leccion();
        servicioLeccion.guardarLeccion(leccion);

        verify(repositorioLeccionMock).guardar(leccion);
    }
}
