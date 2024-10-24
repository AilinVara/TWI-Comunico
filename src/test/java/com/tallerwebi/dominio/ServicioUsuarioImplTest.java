package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioUsuarioImplTest {

    RepositorioUsuario repositorioUsuarioMock;
    ServicioUsuario servicioUsuario;

    @BeforeEach
    public void init(){
        repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioUsuario = new ServicioUsuarioImpl(repositorioUsuarioMock);
    }

    @Test
    public void dadoQueExisteUnUsuarioCuandoLoBuscoPorIdEntoncesDevuelveElUsuario(){
        Usuario usuario = new Usuario();
        when(repositorioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        Usuario usuarioObtenido = servicioUsuario.buscarUsuarioPorId(1L);

        assertThat((usuarioObtenido != null), equalTo(true));
        assertThat(usuarioObtenido, equalTo(usuario));
    }

    @Test
    public void dadoQueBuscoUnUsuarioPorIdQueNoExisteEntoncesDevuelveNull(){
        when(repositorioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(null);

        Usuario usuarioObtenido = servicioUsuario.buscarUsuarioPorId(1L);

        assertThat((usuarioObtenido == null), equalTo(true));
        assertThat(usuarioObtenido, equalTo(null));
    }

}
