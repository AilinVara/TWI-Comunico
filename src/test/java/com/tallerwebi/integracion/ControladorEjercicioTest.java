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
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorEjercicioTest {

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

        this.servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicio);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    private Ejercicio crearEjercicio() {
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setConsigna("Consigna");
        Opcion opcionCorrecta = new Opcion("A");
        Opcion opcionIncorrecta1 = new Opcion("B");
        Opcion opcionIncorrecta2 = new Opcion("C");
        this.sessionFactory.getCurrentSession().save(opcionCorrecta);
        this.sessionFactory.getCurrentSession().save(opcionIncorrecta1);
        this.sessionFactory.getCurrentSession().save(opcionIncorrecta2);
        List<Opcion> opcionesIncorrectas = new ArrayList<>();
        opcionesIncorrectas.add(opcionIncorrecta1);
        opcionesIncorrectas.add(opcionIncorrecta2);
        ejercicio.setOpcionCorrecta(opcionCorrecta);
        ejercicio.setOpcionesIncorrectas(opcionesIncorrectas);
        return ejercicio;
    }

//    @Test
//    @Transactional
//    public void dadoQueExisteUnEjercicioSeDebeRetornarLaPaginaEjercicioYUnEjercicioEnElModeloCuandoSeNavegaAlMappingEjercicio() throws Exception {
//
//        Ejercicio ejercicio = crearEjercicio();
//
//        this.servicioEjercicio.guardarEjercicio(ejercicio);
//
//
//        MvcResult result = this.mockMvc.perform(get("/ejercicio"))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        ModelAndView modelAndView = result.getModelAndView();
//
//        assert modelAndView != null;
//
//        assertThat("ejercicio", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
//        assertThat(false, is(modelAndView.getModel().isEmpty()));
//        assertThat(modelAndView.getModel().get("ejercicio"), equalTo(ejercicio));
//    }

    @Test
    @Transactional
    public void debeRetornarLaVistaEjercicioYVerdaderoEnElModeloCuandoSeResuelveElEjercicioCorrectamente() throws Exception {

        Ejercicio ejercicio = crearEjercicio();

        this.servicioEjercicio.guardarEjercicio(ejercicio);

        MvcResult result = this.mockMvc.perform(post("/resolver")
                        .param("opcionSeleccionada", "1")
                        .param("ejercicioId", "1"))
                        .andExpect(status().isOk())
                        .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assert modelAndView != null;

        assertThat("ejercicio", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
        assertThat(modelAndView.getModel().get("esCorrecta"), equalTo(true));
    }

    @Test
    @Transactional
    public void cuandoNavegoALaRutaEjercicioYEnvioUnIdDeParametroReciboLaVistaEjercicioYElEjercicioConEseIdEnElModelo() throws Exception {

        Ejercicio ejercicio1 = crearEjercicio();
        ejercicio1.setConsigna("Consigna 1");

        Ejercicio ejercicio2 = crearEjercicio();
        ejercicio2.setConsigna("Consigna 2");

        Ejercicio ejercicio3 = crearEjercicio();
        ejercicio3.setConsigna("Consigna 3");

        this.servicioEjercicio.guardarEjercicio(ejercicio1);
        this.servicioEjercicio.guardarEjercicio(ejercicio2);
        this.servicioEjercicio.guardarEjercicio(ejercicio3);

        MvcResult result = this.mockMvc.perform(get("/ejercicio")
                        .param("id", "2"))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        Ejercicio ejercicioEsperado = this.servicioEjercicio.obtenerEjercicio(2L);

        assert modelAndView != null;

        assertThat(modelAndView.getViewName(), equalTo("ejercicio"));
        assertThat(modelAndView.getModel().get("ejercicio"), equalTo(ejercicioEsperado));
    }

}
