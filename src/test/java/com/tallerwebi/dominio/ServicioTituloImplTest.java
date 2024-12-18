package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioTituloImplTest {
    ServicioExperiencia servicioExperienciaMock;
    Usuario usuario;
    Experiencia experiencia;
    RepositorioUsuario repositorioUsuario;
    RepositorioExperiencia repositorioExperienciaMock;
    ServicioTitulo servicioTitulo;
    RepositorioVida repositorioVidaMock;

    private RepositorioProgresoLeccion repositorioProgresoLeccion;


    @BeforeEach
    public void init() {
        usuario = new Usuario();
        experiencia = new Experiencia();

        this.repositorioUsuario = mock(RepositorioUsuario.class);
        this.repositorioExperienciaMock = mock(RepositorioExperiencia.class);
        this.repositorioVidaMock = mock(RepositorioVida.class);
        this.repositorioProgresoLeccion = mock(RepositorioProgresoLeccion.class);
        servicioExperienciaMock = new ServicioExperienciaImpl(repositorioUsuario, repositorioExperienciaMock, repositorioProgresoLeccion);
        servicioTitulo = new ServicioTituloImpl(repositorioUsuario, repositorioVidaMock);
        when(repositorioExperienciaMock.buscarExperienciaPorId(experiencia.getId())).thenReturn(experiencia);
        when(repositorioUsuario.buscarUsuarioPorId(usuario.getId())).thenReturn(usuario);
        //when(usuario.getExperiencia()).thenReturn(experiencia);
        usuario.setExperiencia(experiencia);

        //when(servicioExperienciaMock.obtenerExperiencia(usuario.getId())).thenReturn(experiencia);

    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioGana100XPTeniendo400XPObtieneElTituloDeNovato() {
        experiencia.setCantidadExperiencia(400);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuario.guardar(usuario);

        this.servicioExperienciaMock.ganar100DeExperiencia(usuario.getId());
        this.repositorioExperienciaMock.actualizarExperiencia(experiencia);

        assertThat(experiencia.getCantidadExperiencia(), equalTo(500));

        this.servicioTitulo.actualizarTituloSegunExperiencia(usuario.getId());

        assertThat(usuario.getTitulo(), equalTo("Novato"));
    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioGana100XPTeniendo4900XPObtieneElTituloDeComunicador() {
        experiencia.setCantidadExperiencia(4900);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuario.guardar(usuario);

        this.servicioExperienciaMock.ganar100DeExperiencia(usuario.getId());
        this.repositorioExperienciaMock.actualizarExperiencia(experiencia);

        assertThat(experiencia.getCantidadExperiencia(), equalTo(5000));

        this.servicioTitulo.actualizarTituloSegunExperiencia(usuario.getId());

        assertThat(usuario.getTitulo(), equalTo("Comunicador"));
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioExisteYLaExperienciaQueTieneEsMenorA500XPQueElPrimerTituloConElQueVengaSeaConElTituloDePrincipiante() {
        experiencia.setCantidadExperiencia(0);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuario.guardar(usuario);

        this.servicioTitulo.actualizarTituloSegunExperiencia(usuario.getId());

        assertThat(usuario.getTitulo(), equalTo("Principiante"));
    }
}


