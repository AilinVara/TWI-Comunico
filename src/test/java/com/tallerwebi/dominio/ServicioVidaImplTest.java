//package com.tallerwebi.dominio;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.test.annotation.Rollback;
//
//import javax.transaction.Transactional;
//
//import java.time.LocalDateTime;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class ServicioVidaImplTest {
//
//    ServicioVida servicioVida;
//    RepositorioVida repositorioVidaMock;
//    Vida vidaMock;
//
//    @BeforeEach
//    public void init(){
//
//        vidaMock = new Vida(5);
//        this.repositorioVidaMock = mock(RepositorioVida.class);
//        servicioVida = new ServicioVidaImpl(repositorioVidaMock);
//        when(repositorioVidaMock.buscarUnaVidaPorId(vidaMock.getId())).thenReturn(vidaMock);
//
//
//    }
//    @Test
//    @Rollback
//    @Transactional
//    public void dadoQueElUsuarioTieneVidasCuandoPierdeUnaVidaEntoncesCantidadDeVidasDisminuyeEnUno() {
//        int cantidadDeVidasActuales = 3;
//        Vida vida = new Vida(cantidadDeVidasActuales);
//        this.repositorioVidaMock.guardarUnaVida(vida);
//
//        Usuario usuario = new Usuario();
//        usuario.setVida(vida);
//
//        Boolean resultado = this.servicioVida.perderUnaVida(usuario);
//
//        assertThat(resultado, equalTo(Boolean.TRUE));
//        assertThat(vida.getCantidadDeVidasActuales(), equalTo(2));
//    }
//
////    @Test
////    @Rollback
////    @Transactional
////    public void dadoQueElUsuarioTieneVidasCuandoGanaUnaVidaEntoncesLaCantidadDeVidasAumentaEnUno(){
////        int cantidadDeVidasActuales = 4;
////        Vida vida = new Vida(cantidadDeVidasActuales);
////        vida.setUltimaVezQueSeRegeneroLaVida(LocalDateTime.now().minusMinutes(2));
////       // System.out.println("Ultima vez que se regeneró la vida: " + vida.getUltimaVezQueSeRegeneroLaVida());//Pasaron 2 minutos
////        this.repositorioVidaMock.guardarUnaVida(vida);
////
////        Boolean regenerada = this.servicioVida.regenerarUnaVida(vida.getId());
////
////        Vida vidaActualizada = this.repositorioVidaMock.buscarUnaVidaPorId(vida.getId());
////        assertThat(regenerada, equalTo(Boolean.TRUE));
////        assertThat(vidaActualizada.getCantidadDeVidasActuales(), equalTo(5));
////    }
//    @Test
//    @Rollback
//    @Transactional
//    public void dadoQueUsuarioTieneMaximoDeVidasCuandoSeRegeneraEntoncesCantidadDeVidasNoSuperaLaCantidadDeVidasMaximas() {
//        int cantidadDeVidasActuales = 5;
//        Vida vida = new Vida(cantidadDeVidasActuales);
//        vida.setUltimaVezQueSeRegeneroLaVida(LocalDateTime.now().minusMinutes(2));
//        this.repositorioVidaMock.guardarUnaVida(vida);
//
//        Boolean regenerada = this.servicioVida.regenerarUnaVida(vida.getId());
//
//        Vida vidaActualizada = this.repositorioVidaMock.buscarUnaVidaPorId(vida.getId());
//        assertThat(regenerada, equalTo(Boolean.FALSE));
//        assertThat(vidaActualizada.getCantidadDeVidasActuales(), equalTo(5));
//
//    }
//
//}
