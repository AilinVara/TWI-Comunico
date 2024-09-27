package com.tallerwebi.integracion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.RepositorioEjercicio;
import com.tallerwebi.dominio.ServicioEjercicio;
import com.tallerwebi.dominio.ServicioEjercicioImpl;
import com.tallerwebi.infraestructura.RepositorioEjercicioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.ControladorEjercicio;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;


import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorEjercicioTest {

    private Ejercicio ejercicio;
    private ServicioEjercicio servicioEjercicio;
    private RepositorioEjercicio repositorioEjercicio;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @BeforeEach
    public void init(){
        this.repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
        this.ejercicio = new Ejercicio();
        this.servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicio);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    @Test
    public void debeRetornarLaPaginaEjercicioCuandoSeNavegaAlMappingEjercicio() throws Exception {

        this.servicioEjercicio.guardarEjercicio(this.ejercicio);

        MvcResult result = this.mockMvc.perform(get("/ejercicio"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assert modelAndView != null;

        assertThat("ejercicio", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
    }

}
