package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

public class ServicioVidaImplTest {

    ServicioVida servicioVida;
    RepositorioVida repositorioVidaMock;
    Vida vidaMock;
    Usuario usuarioMock;
    RepositorioUsuario repositorioUsuarioMock;
    ServicioTitulo servicioTitulo;

    @BeforeEach
    public void init() {

        vidaMock = mock(Vida.class);
        usuarioMock = mock(Usuario.class);
        this.repositorioVidaMock = mock(RepositorioVida.class);
        this.repositorioUsuarioMock = mock(RepositorioUsuario.class);
        servicioTitulo = new ServicioTituloImpl(repositorioUsuarioMock,repositorioVidaMock);
        servicioVida = new ServicioVidaImpl(repositorioVidaMock, repositorioUsuarioMock, servicioTitulo);
        when(repositorioVidaMock.buscarUnaVidaPorId(vidaMock.getId())).thenReturn(vidaMock);
        when(repositorioUsuarioMock.buscarUsuarioPorId(usuarioMock.getId())).thenReturn(usuarioMock);
        when(usuarioMock.getVida()).thenReturn(vidaMock);
        usuarioMock.getTitulo();
        when(servicioVida.obtenerVida(usuarioMock.getId())).thenReturn(vidaMock);


    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueElUsuarioTieneVidasCuandoPierdeUnaVidaEntoncesCantidadDeVidasDisminuyeEnUno() {

        when(vidaMock.getCantidadDeVidasActuales()).thenReturn(3);
        //vidaMock.setCantidadDeVidasActuales(3);

        this.repositorioVidaMock.guardarUnaVida(vidaMock);
        this.repositorioUsuarioMock.guardar(usuarioMock);

        Boolean resultado = this.servicioVida.perderUnaVida(usuarioMock.getId());

        assertThat(resultado, equalTo(Boolean.TRUE));
        //assertThat(vidaMock.getCantidadDeVidasActuales(), equalTo(2));
    }

    //    @Test
//    @Rollback
//    @Transactional
//    public void dadoQueElUsuarioTieneVidasCuandoGanaUnaVidaEntoncesLaCantidadDeVidasAumentaEnUno(){
//        int cantidadDeVidasActuales = 4;
//        Vida vida = new Vida(cantidadDeVidasActuales);
//        vida.setUltimaVezQueSeRegeneroLaVida(LocalDateTime.now().minusMinutes(2));
//       // System.out.println("Ultima vez que se regeneró la vida: " + vida.getUltimaVezQueSeRegeneroLaVida());//Pasaron 2 minutos
//        this.repositorioVidaMock.guardarUnaVida(vida);
//
//        Boolean regenerada = this.servicioVida.regenerarUnaVida(vida.getId());
//
//        Vida vidaActualizada = this.repositorioVidaMock.buscarUnaVidaPorId(vida.getId());
//        assertThat(regenerada, equalTo(Boolean.TRUE));
//        assertThat(vidaActualizada.getCantidadDeVidasActuales(), equalTo(5));
//    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueUsuarioTieneMaximoDeVidasCuandoSeRegeneraEntoncesCantidadDeVidasNoSuperaLaCantidadDeVidasMaximas() {

        Vida vidaReal = new Vida(5);
        vidaReal.setUltimaVezQueSeRegeneroLaVida(LocalDateTime.now().minusMinutes(2));
        when(usuarioMock.getTitulo()).thenReturn("Novato");
        when(usuarioMock.getVida()).thenReturn(vidaReal);
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioMock);
        when(this.repositorioUsuarioMock.buscarTodos()).thenReturn(usuarios);


        this.servicioVida.regenerarVidasDeTodosLosUsuarios();

        assertThat(vidaReal.getCantidadDeVidasActuales(), equalTo(5));
    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueUsuarioConTituloNovatoRegeneraSuVida5MinutosYElDefaultEsDe2HorasQueLaRegenereCorrectamenteEnEseTiempoEstipulado() {
        Vida vidaReal = new Vida(4);
        vidaReal.setUltimaVezQueSeRegeneroLaVida(LocalDateTime.now().minusMinutes(115)); // 1 hora y 55 minutos atrás

        when(usuarioMock.getTitulo()).thenReturn("Novato");
        when(usuarioMock.getVida()).thenReturn(vidaReal);

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuarioMock);

        when(this.repositorioUsuarioMock.buscarTodos()).thenReturn(usuarios);


        this.servicioVida.regenerarVidasDeTodosLosUsuarios();

        assertThat(vidaReal.getCantidadDeVidasActuales(), equalTo(5));
    }


}
