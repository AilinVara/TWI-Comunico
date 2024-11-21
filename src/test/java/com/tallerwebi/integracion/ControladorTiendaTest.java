package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.RepositorioVidaImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorTiendaTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private MockHttpSession sessionMock;
    private ServicioUsuario servicioUsuario;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioVida repositorioVida;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.sessionMock = new MockHttpSession();
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        this.repositorioVida = new RepositorioVidaImpl(sessionFactory);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueUsuarioCompraComunicoPointsCuandoSeProcesaLaSolicitudDebeRedirigirAMercadoPago() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/comprarComunicoPoints")
                        .param("cantidad", "500")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        String redirectUrl = result.getResponse().getRedirectedUrl();
        assertNotNull(redirectUrl);
        assertEquals(true, redirectUrl.contains("sandbox"));
    }

//    @Test
//    @Transactional
//    @Rollback
//    public void dadoQueCompraEsAprobadaCuandoUsuarioVuelveDeMercadoPagoDebeIncrementarSusComunicoPoints() throws Exception {
//        Vida vida = new Vida();
//        Usuario usuario = new Usuario();
//        usuario.setVida(vida);
//        usuario.setComunicoPoints(10);
//
//        this.sessionFactory.getCurrentSession().save(vida);
//        this.sessionFactory.getCurrentSession().save(usuario);
//
//        sessionMock.setAttribute("id", usuario.getId());
//
//        MvcResult result = this.mockMvc.perform(get("/comprarComunicoPoints/Pagar")
//                        .param("collection_status", "approved")
//                        .param("cantidad", "500")
//                        .session(sessionMock))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Usuario usuarioActualizado = this.servicioUsuario.buscarUsuarioPorId(usuario.getId());
//        assertEquals(510, usuarioActualizado.getComunicoPoints().intValue());
//
//        ModelAndView modelAndView = result.getModelAndView();
//        assertNotNull(modelAndView);
//        assertEquals("tienda", modelAndView.getViewName());
//    }
//
//
//    @Test
//    @Transactional
//    @Rollback
//    public void dadoQueCompraNoEsAprobadaCuandoUsuarioVuelveDeMercadoPagoDebeMostrarErrorYSusComunicoPointsNoCambian() throws Exception {
//        Usuario usuario = new Usuario();
//        usuario.setComunicoPoints(10);
//        this.sessionFactory.getCurrentSession().save(usuario);
//
//        sessionMock.setAttribute("id", usuario.getId());
//        MvcResult result = this.mockMvc.perform(get("/comprarComunicoPoints/Pagar")
//                        .param("collection_status", "failure")
//                        .param("cantidad", "500")
//                        .session(sessionMock))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        Usuario usuarioActualizado = this.servicioUsuario.buscarUsuarioPorId(usuario.getId());
//        assertEquals(10, usuarioActualizado.getComunicoPoints().intValue());
//
//        ModelAndView modelAndView = result.getModelAndView();
//        assertNotNull(modelAndView);
//        assertEquals("tienda", modelAndView.getViewName());
//    }
}