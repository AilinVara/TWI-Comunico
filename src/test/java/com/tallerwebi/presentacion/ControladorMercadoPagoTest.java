package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorMercadoPagoTest {

    private ControladorMercadoPago controladorMercadoPago;
    private ServicioUsuario servicioUsuarioMock;
    private HttpServletRequest requestMock;
    private HttpSession sessionMock;

    @BeforeEach
    public void init(){
        servicioUsuarioMock = mock(ServicioUsuario.class);
        requestMock = mock(HttpServletRequest.class);
        sessionMock = mock(HttpSession.class);
        controladorMercadoPago = new ControladorMercadoPago(servicioUsuarioMock);
    }

    @Test
    public void dadoQueLaCompraEsExitosaEntoncesSeActualizanLosPuntosYSeMuestraMensajeDeExito() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "approved");
        params.put("quantity", "10");

        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(50);

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        ModelAndView mav = controladorMercadoPago.compra(params, requestMock);

        assertEquals("Compra realizada satisfactoriamente.", mav.getModel().get("alerta"));
        assertEquals("success", mav.getModel().get("tipoAlerta"));
        assertEquals(60, mav.getModel().get("points"));
    }

    @Test
    public void dadoQueLaCompraFallaEntoncesSeMuestraMensajeDeError() {
        Map<String, String> params = new HashMap<>();
        params.put("status", "failure");
        params.put("quantity", "10");

        Usuario usuario = new Usuario();

        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("id")).thenReturn(1L);
        when(servicioUsuarioMock.buscarUsuarioPorId(1L)).thenReturn(usuario);

        ModelAndView mav = controladorMercadoPago.compra(params, requestMock);

        assertEquals("No pudimos procesar la compra.", mav.getModel().get("alerta"));
        assertEquals("error", mav.getModel().get("tipoAlerta"));
    }
}
