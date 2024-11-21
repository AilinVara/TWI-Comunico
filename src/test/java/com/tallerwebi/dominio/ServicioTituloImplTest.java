package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioTituloImplTest {
    ServicioExperiencia servicioExperienciaMock;
    Usuario usuario;
    Experiencia experiencia;
    RepositorioUsuario repositorioUsuarioMock;
    RepositorioExperiencia repositorioExperienciaMock;
    ServicioTitulo servicioTitulo;
    RepositorioVida repositorioVidaMock;
    ServicioVida servicioVida;
    Vida vidaMock;

    private RepositorioProgresoLeccion repositorioProgresoLeccion;


    @BeforeEach
    public void init() {
        usuario = new Usuario();
        experiencia = new Experiencia();

        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        this.repositorioExperienciaMock = mock(RepositorioExperiencia.class);
        this.repositorioVidaMock = mock(RepositorioVida.class);
        this.repositorioProgresoLeccion = mock(RepositorioProgresoLeccion.class);
        servicioExperienciaMock = new ServicioExperienciaImpl(repositorioUsuarioMock, repositorioExperienciaMock, repositorioProgresoLeccion);
        servicioTitulo = new ServicioTituloImpl(repositorioUsuarioMock, repositorioVidaMock);
        when(repositorioExperienciaMock.buscarExperienciaPorId(experiencia.getId())).thenReturn(experiencia);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuario.getId())).thenReturn(usuario);
        usuario.setExperiencia(experiencia);

    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioGana100XPTeniendo400XPObtieneElTituloDeNovato() {
        experiencia.setCantidadExperiencia(400);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuarioMock.guardar(usuario);

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
        this.repositorioUsuarioMock.guardar(usuario);

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
        this.repositorioUsuarioMock.guardar(usuario);

        this.servicioTitulo.actualizarTituloSegunExperiencia(usuario.getId());

        assertThat(usuario.getTitulo(), equalTo("Principiante"));
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioExisteYLaExperienciaQueTieneEsMenorA500XPQueCuandoSupereLos500XpElUsuarioGane20ComunicoPoints() {
        usuario.setComunicoPoints(0);
        experiencia.setCantidadExperiencia(400);
        this.repositorioExperienciaMock.guardarExperiencia(experiencia);
        this.repositorioUsuarioMock.guardar(usuario);
        this.servicioExperienciaMock.ganar100DeExperiencia(usuario.getId());
        this.servicioTitulo.actualizarTituloSegunExperiencia(usuario.getId());
        this.servicioTitulo.obtenerComunicoPointsCuandoConsigueTitulo(usuario.getId());

        assertThat(usuario.getTitulo(), equalTo("Novato"));
        assertThat(usuario.getComunicoPoints(), equalTo(20));
    }
}


