package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioEjercicioImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorEjercicioTest {

    private ServicioEjercicio servicioEjercicio;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @BeforeEach
    public void init(){
        RepositorioEjercicio repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);

        this.servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicio);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenTresOpcionesYUnEjercicioEnLaBaseDeDatosDebeRetornarLaPaginaEjercicioYElEjercicioCuandoSeNavegaAlMappingEjercicio() throws Exception {
        Opcion opcionCorrecta = new Opcion("A");
        Opcion opcion1 = new Opcion("B");
        Opcion opcion2 = new Opcion("C");

        sessionFactory.getCurrentSession().save(opcionCorrecta);
        sessionFactory.getCurrentSession().save(opcion1);
        sessionFactory.getCurrentSession().save(opcion2);

        List<Opcion> opcionesIncorrectas = new ArrayList<>();
        opcionesIncorrectas.add(opcion1);
        opcionesIncorrectas.add(opcion2);

        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setConsigna("Consigna");
        ejercicio.setOpcionCorrecta(opcionCorrecta);
        ejercicio.setOpcionesIncorrectas(opcionesIncorrectas);

        this.servicioEjercicio.guardarEjercicio(ejercicio);

        MvcResult result = this.mockMvc.perform(get("/ejercicio"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assert modelAndView != null;

        assertThat("ejercicio", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
    }

}
