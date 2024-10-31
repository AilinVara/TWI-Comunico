package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class ServicioProgresoLeccionImplTest {

    private RepositorioProgresoLeccion repositorioMock;
    private ServicioLogin servicioUsuarioMock;
    private ServicioLeccion servicioLeccionMock;
    private ServicioProgresoLeccion servicioProgresoLeccion;

    @BeforeEach
    public void init(){
        repositorioMock = mock(RepositorioProgresoLeccion.class);
        servicioProgresoLeccion = new ServicioProgresoLeccionImpl(repositorioMock, servicioUsuarioMock, servicioLeccionMock);
    }

    @Test
    public void dadoUnListaDeProgresosCuandoTodosEstanCompletosEntoncesDevuelveTrue() {
        ProgresoLeccion progreso1 = new ProgresoLeccion();
        progreso1.setCompleto(true);
        ProgresoLeccion progreso2 = new ProgresoLeccion();
        progreso2.setCompleto(true);

        List<ProgresoLeccion> progresos = new ArrayList<>();
        progresos.add(progreso1);
        progresos.add(progreso2);

        boolean resultado = servicioProgresoLeccion.verificarCompletado(progresos);

        assertThat(resultado, equalTo(true));
    }

    @Test
    public void dadoUnaListaDeProgresosCuandoAlMenosUnoNoEstaCompletoEntoncesDevuelveFalse() {
        ProgresoLeccion progreso1 = new ProgresoLeccion();
        progreso1.setCompleto(true);
        ProgresoLeccion progreso2 = new ProgresoLeccion();
        progreso2.setCompleto(false);

        List<ProgresoLeccion> progresos = new ArrayList<>();
        progresos.add(progreso1);
        progresos.add(progreso2);

        boolean resultado = servicioProgresoLeccion.verificarCompletado(progresos);

        assertThat(resultado, equalTo(false));
    }


}
