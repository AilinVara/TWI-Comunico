package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.not;

public class ServicioExperienciaImplTest {

    ServicioExperiencia servicioExperiencia;
    Usuario usuarioMock;
    Experiencia experiencia;
    RepositorioUsuario repositorioUsuarioMock;
    RepositorioExperiencia repositorioExperienciaMock;
    RepositorioProgresoLeccion repositorioProgresoLeccionMock;
    ServicioLeccion servicioLeccionMock;
    ServicioProgresoLeccion servicioProgresoLeccion;
    ServicioLogin servicioUsuarioMock;
    Leccion leccion;
    RepositorioLeccion repositorioLeccionMock;
    ServicioUsuario servicioUsuario;


    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        experiencia = new Experiencia();
        leccion = new Leccion();
        leccion.setId(1L);
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.repositorioExperienciaMock = mock(RepositorioExperiencia.class);
        this.repositorioProgresoLeccionMock = mock(RepositorioProgresoLeccion.class);
        this.repositorioLeccionMock = mock(RepositorioLeccion.class);
        this.servicioUsuarioMock = mock(ServicioLogin.class);
        this.servicioLeccionMock = mock(ServicioLeccion.class);
        servicioExperiencia = new ServicioExperienciaImpl(repositorioUsuarioMock, repositorioExperienciaMock, repositorioProgresoLeccionMock);
        servicioProgresoLeccion = new ServicioProgresoLeccionImpl(repositorioProgresoLeccionMock, servicioUsuarioMock, servicioLeccionMock,servicioExperiencia);
        //servicioLeccionMock = new ServicioLeccionImpl(repositorioLeccionMock);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
        when(repositorioExperienciaMock.buscarExperienciaPorId(experiencia.getId())).thenReturn(experiencia);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(usuarioMock.getExperiencia()).thenReturn(experiencia);
        when(servicioExperiencia.obtenerExperiencia(usuarioMock.getId())).thenReturn(experiencia);


    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioTiene5000DeExperienciaNoPuedeSeguirAumentandoEsaCantidadCuandoGana100XP() {

        experiencia.setCantidadExperiencia(5000);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuarioMock.guardar(usuarioMock);

        this.servicioExperiencia.ganar100DeExperiencia(usuarioMock.getId());
        this.repositorioExperienciaMock.actualizarExperiencia(experiencia);

        assertThat(experiencia.getCantidadExperiencia(), not(equalTo(5100)));
        assertThat(experiencia.getCantidadExperiencia(), equalTo(5000));


    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioTiene4800DeExperienciaDebeSeguirAumentandoEsaCantidadCuandoGana100XP() {
        experiencia.setCantidadExperiencia(4800);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);

        this.repositorioUsuarioMock.guardar(usuarioMock);
        this.servicioExperiencia.ganar100DeExperiencia(usuarioMock.getId());



        assertThat(experiencia.getCantidadExperiencia(), equalTo(4900));

    }

        @Test
        @Rollback
        @Transactional
        public void dadoQueElUsuarioCompletaUnaLeccionCorrectamentePorPrimeraVezGana300XP() {
            experiencia.setCantidadExperiencia(0);
            this.repositorioExperienciaMock.guardarExperiencia(experiencia);

            this.servicioProgresoLeccion.verificarCompletadoPorLeccion(leccion.getId(), usuarioMock.getId());

            this.servicioProgresoLeccion.otorgarExperienciaPorLeccion(usuarioMock.getId(),leccion.getId());

            this.repositorioUsuarioMock.guardar(usuarioMock);


            assertThat(experiencia.getCantidadExperiencia(), equalTo(300));

    }


}
