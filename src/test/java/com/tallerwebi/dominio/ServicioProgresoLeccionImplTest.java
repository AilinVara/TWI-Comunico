package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioProgresoLeccionImplTest {

    private RepositorioProgresoLeccion repositorioMock;
    private ServicioLogin servicioUsuarioMock;
    private ServicioLeccion servicioLeccionMock;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioExperiencia servicioExperiencia;

    @BeforeEach
    public void init(){
        repositorioMock = mock(RepositorioProgresoLeccion.class);
        servicioProgresoLeccion = new ServicioProgresoLeccionImpl(repositorioMock, servicioUsuarioMock, servicioLeccionMock,servicioExperiencia);
    }

    @Test
    public void dadoUnProgresoLeccionCuandoLoGuardoEntoncesSeLlamaAlMetodoGuardarDelRepositorio() {
        ProgresoLeccion progresoLeccion = new ProgresoLeccion();

        servicioProgresoLeccion.guardarProgresoLeccion(progresoLeccion);

        verify(repositorioMock).guardar(progresoLeccion);
    }

    @Test
    public void dadoUnosIdsCuandoBuscoeElProgresoPorIdsEntoncesDevuelveElProgresoLeccionCorrecto() {
        ProgresoLeccion progresoLeccion = new ProgresoLeccion();
        when(repositorioMock.buscarPorIds(1L, 2L, 3L)).thenReturn(progresoLeccion);

        ProgresoLeccion resultado = servicioProgresoLeccion.buscarPorIds(1L, 2L, 3L);

        assertThat(resultado, equalTo(progresoLeccion));
    }

    @Test
    public void dadoUnProgresoLeccionYResueltoCuandoActualizoEntoncesElProgresoSeMarcaComoCompleto() {
        ProgresoLeccion progresoLeccion = new ProgresoLeccion();
        servicioProgresoLeccion.actualizarProgreso(progresoLeccion, true);

        assertThat(progresoLeccion.getCompleto(), equalTo(true));
        verify(repositorioMock).actualizar(progresoLeccion);
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
