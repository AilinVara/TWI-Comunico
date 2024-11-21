package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SuscriptoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioSuscripcionImplTest {

    RepositorioUsuario repositorioUsuario;
    RepositorioSuscripcion repositorioSuscripcion;
    ServicioSuscripcion servicioSuscripcion;
    RepositorioDescuento repositorioDescuento;
    RepositorioTipoSuscripcion repositorioTipoSuscripcion;

    @BeforeEach
    public void init() {
        repositorioUsuario = mock(RepositorioUsuario.class);
        repositorioSuscripcion = mock(RepositorioSuscripcion.class);
        repositorioDescuento = mock(RepositorioDescuento.class);
        servicioSuscripcion = spy(new ServicioSuscripcionImpl(repositorioSuscripcion, repositorioUsuario, repositorioDescuento, repositorioTipoSuscripcion));
    }

    @Test
    @Rollback
    public void dadoQueUnUsuarioNoTieneSuscripcionQuePuedaComprarSuscripcionBasica() throws SuscriptoException {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(1000);
        usuario.setAyudas(3);
        Suscripcion suscripcionBasica = new Suscripcion();
        suscripcionBasica.setId(2L);

        when(repositorioSuscripcion.buscarSuscripcion(2L)).thenReturn(suscripcionBasica);

        doReturn(true).when(servicioSuscripcion).verificarSuscripcion(usuario);

        servicioSuscripcion.comprarSuscripcionBasica(usuario);

        assertEquals(suscripcionBasica, usuario.getSuscripcion());
        assertNotNull(usuario.getSuscripcion().getFechaCompra());
        assertNotNull(usuario.getSuscripcion().getFechaVencimiento());
        assertEquals(LocalDateTime.now().plusMonths(1).getMonth(), usuario.getSuscripcion().getFechaVencimiento().getMonth());

        verify(repositorioUsuario).modificar(usuario);
    }

    @Test
    @Rollback
    public void dadoQueUnUsuarioTieneSuscripcionQueNoPuedaComprarSuscripcionBasica() {
        Usuario usuario = new Usuario();

        doReturn(false).when(servicioSuscripcion).verificarSuscripcion(usuario);

        assertThrows(SuscriptoException.class, () -> servicioSuscripcion.comprarSuscripcionBasica(usuario));

        verify(repositorioUsuario, never()).modificar(usuario);
    }

}

