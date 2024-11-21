package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioVida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vida;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ControladorTiendaTest {

    private ControladorTienda controladorTienda;
    private ServicioUsuario servicioUsuarioMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;
    private RedirectAttributes flashMapMock;
    private ServicioVida servicioVidaMock;

    @BeforeEach
    public void init() {
        servicioUsuarioMock = mock(ServicioUsuario.class);
        servicioVidaMock = mock(ServicioVida.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        controladorTienda = new ControladorTienda(servicioUsuarioMock, servicioVidaMock);
        flashMapMock = mock(RedirectAttributes.class);
    }

    @Test
    public void dadoQueElUsuarioCompraVidasYTieneSaldoSuficienteEntoncesSeActualizanLasVidasYElSaldo() {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(1000);
        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(2);
        usuario.setVida(vida);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        RedirectAttributes flashMock = mock(RedirectAttributes.class);
        Map<String, Object> flashAttributes = new HashMap<>();

        when(flashMock.addFlashAttribute(anyString(), any())).thenAnswer(invocation -> {
            flashAttributes.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        });

        ModelAndView mav = controladorTienda.comprarVidas(3, requestMock, flashMock);

        assertEquals(500, usuario.getComunicoPoints());
        assertEquals(5, usuario.getVida().getCantidadDeVidasActuales());
        assertEquals("redirect:/tienda", mav.getViewName());
    }
    @Test
    public void dadoQueElUsuarioCompraVidasYNoTieneSaldoSuficienteEntoncesSeMuestraMensajeDeError() {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(100);
        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(2);
        usuario.setVida(vida);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        RedirectAttributes flashMock = mock(RedirectAttributes.class);
        Map<String, Object> flashAttributes = new HashMap<>();

        when(flashMock.addFlashAttribute(anyString(), any())).thenAnswer(invocation -> {
            flashAttributes.put(invocation.getArgument(0), invocation.getArgument(1));
            return null;
        });

        ModelAndView mav = controladorTienda.comprarVidas(3, requestMock, flashMock);

        assertEquals(100, usuario.getComunicoPoints());
        assertEquals(2, usuario.getVida().getCantidadDeVidasActuales());
        assertEquals("redirect:/tienda", mav.getViewName());
        assertEquals("Saldo insuficiente. Necesitás 400 ComunicoPoints más.", flashAttributes.get("error"));
    }


    @Test
    public void dadoQueElUsuarioVisitaLaTiendaEntoncesSeMuestraSuSaldo() {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(750);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        ModelAndView mav = controladorTienda.tienda(requestMock);

        assertEquals(750, mav.getModel().get("saldo"));
        assertEquals("tienda", mav.getViewName());
    }

}