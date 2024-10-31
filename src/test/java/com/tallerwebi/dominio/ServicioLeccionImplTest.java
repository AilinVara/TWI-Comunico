package com.tallerwebi.dominio;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioLeccionImplTest {

    RepositorioLeccion repositorioLeccionMock;
    ServicioLeccion servicioLeccion;

    @BeforeEach
    public void init() {
        repositorioLeccionMock = mock(RepositorioLeccion.class);
        servicioLeccion = new ServicioLeccionImpl(repositorioLeccionMock);
    }

    @Test
    public void dadoQueExisteUnaLeccionCuandoLaGuardoEntoncesSeLlamaAlMetodoGuardarDelRepositorioLeccion() {
        Leccion leccion = new Leccion();
        servicioLeccion.guardarLeccion(leccion);

        verify(repositorioLeccionMock).guardar(leccion);
    }

    @Test
    public void dadoQueExisteUnaLeccionCuandoLaBuscoPorSuIdEntoncesLaObtengo() {
        Leccion leccion = new Leccion();
        when(repositorioLeccionMock.buscarPorId(leccion.getId())).thenReturn(leccion);

        Leccion leccionObtenida = servicioLeccion.obtenerLeccion(leccion.getId());

        assertThat(leccionObtenida, equalTo(leccion));
    }

}
