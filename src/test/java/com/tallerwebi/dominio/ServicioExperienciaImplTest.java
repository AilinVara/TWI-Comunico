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

    ServicioExperiencia servicioExperienciaMock;
    Usuario usuarioMock;
    Experiencia experiencia;
    RepositorioUsuario repositorioUsuarioMock;
    RepositorioExperiencia repositorioExperienciaMock;
    RepositorioProgresoLeccion repositorioProgresoLeccion;

    @BeforeEach
    public void init() {
        usuarioMock = mock(Usuario.class);
        experiencia = new Experiencia();  // Usa una instancia real

        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.repositorioExperienciaMock = mock(RepositorioExperiencia.class);
        this.repositorioProgresoLeccion = mock(RepositorioProgresoLeccion.class);
        servicioExperienciaMock = new ServicioExperienciaImpl(repositorioUsuarioMock, repositorioExperienciaMock, repositorioProgresoLeccion);
        when(repositorioExperienciaMock.buscarExperienciaPorId(experiencia.getId())).thenReturn(experiencia);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(usuarioMock.getExperiencia()).thenReturn(experiencia);
        when(servicioExperienciaMock.obtenerExperiencia(usuarioMock.getId())).thenReturn(experiencia);

    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioTiene5000DeExperienciaNoPuedeSeguirAumentandoEsaCantidadCuandoGana100XP() {

        experiencia.setCantidadExperiencia(5000);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuarioMock.guardar(usuarioMock);

        this.servicioExperienciaMock.ganar100DeExperiencia(usuarioMock.getId());
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
        this.servicioExperienciaMock.ganar100DeExperiencia(usuarioMock.getId());



        assertThat(experiencia.getCantidadExperiencia(), equalTo(4900));

    }

}
