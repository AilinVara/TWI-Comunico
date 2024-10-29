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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorMercadoPagoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private MockHttpSession sessionMock;
    private ServicioUsuario servicioUsuario;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioVida repositorioVida;
    private ServicioVida servicioVida;

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.sessionMock = new MockHttpSession();
        this.repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        this.servicioUsuario = new ServicioUsuarioImpl(repositorioUsuario);
        this.repositorioVida = new RepositorioVidaImpl(sessionFactory);
        this.servicioVida = new ServicioVidaImpl(repositorioVida,repositorioUsuario);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueUsuarioSolicitaComprarComunicoPointsCuandoEnvioCantidadValidaDebeRetornarElIdDePreferencia() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/mp/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String preferenceId = result.getResponse().getContentAsString();

        assertNotNull(preferenceId);
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueCompraEsAprobadaCuandoUsuarioVuelveDeMercadoPagoDebeAumentarSusComunicoPoints() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(10);
        this.sessionFactory.getCurrentSession().save(usuario);
        sessionMock.setAttribute("id", usuario.getId());

        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(5);
        usuario.setVida(vida);
        this.sessionFactory.getCurrentSession().save(vida);
        this.sessionFactory.getCurrentSession().update(usuario);
        MvcResult result = this.mockMvc.perform(get("/comprar")
                        .param("status", "approved")
                        .param("quantity", "5")
                        .session(sessionMock))
                .andExpect(status().isOk())
                .andReturn();

        Usuario usuarioActualizado = this.servicioUsuario.buscarUsuarioPorId(usuario.getId());

        assertEquals(15, usuarioActualizado.getComunicoPoints().intValue());

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertEquals("comprarPoints", modelAndView.getViewName());

        assertEquals("Compra realizada satisfactoriamente.", modelAndView.getModel().get("alerta"));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueCompraNoEsAprobadaCuandoUsuarioVuelveDeMercadoPagoDebeMostrarErrorYSusComunicoPointsSiguenIgual() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setComunicoPoints(10);
        this.sessionFactory.getCurrentSession().save(usuario);

        sessionMock.setAttribute("id", usuario.getId());
        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(5);
        usuario.setVida(vida);
        this.sessionFactory.getCurrentSession().save(vida);
        this.sessionFactory.getCurrentSession().update(usuario);

        MvcResult result = this.mockMvc.perform(get("/comprar")
                        .param("status", "failure")
                        .param("quantity", "5")
                        .session(sessionMock))
                .andExpect(status().isOk())
                .andReturn();

        Usuario usuarioActualizado = this.servicioUsuario.buscarUsuarioPorId(usuario.getId());
        assertEquals(10, usuarioActualizado.getComunicoPoints().intValue());

        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView);
        assertEquals("comprarPoints", modelAndView.getViewName());
        assertEquals("No pudimos procesar la compra.", modelAndView.getModel().get("alerta"));
    }
}
