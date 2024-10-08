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
    private MockHttpSession sessionMock;

    @BeforeEach
    public void init(){
        this.repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
        this.servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicio);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.sessionMock = new MockHttpSession();
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaLeccionConEjerciciosCuandoUnUsuarioResuelveCorrectamenteUnEjercicioSeDebeRetornarLaVistaEjercicioYVerdaderoEnElModelo() throws Exception {
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);

        Leccion leccion = crearLeccion();
        this.sessionFactory.getCurrentSession().save(leccion);


        sessionMock.setAttribute("id", usuario.getId());

        MvcResult result = this.mockMvc.perform(post("/resolver/1?leccion=" + leccion.getId().toString())
                        .param("opcionSeleccionada", "1")
                        .param("ejercicioId", "1")
                        .param("leccion",leccion.getId().toString())
                        .session(sessionMock))
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
    @Rollback
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConTresEjerciciosCuandoNavegoALaRutaEjercicioYEnvioUnIdDeParametroReciboLaVistaEjercicioYElEjercicioConEseIdEnElModelo() throws Exception {
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        sessionMock.setAttribute("id", usuario.getId());

        Ejercicio ejercicio1 = crearEjercicio();
        ejercicio1.setConsigna("Consigna 1");

        Ejercicio ejercicio2 = crearEjercicio();
        ejercicio2.setConsigna("Consigna 2");

        Ejercicio ejercicio3 = crearEjercicio();
        ejercicio3.setConsigna("Consigna 3");

        this.servicioEjercicio.guardarEjercicio(ejercicio1);
        this.servicioEjercicio.guardarEjercicio(ejercicio2);
        this.servicioEjercicio.guardarEjercicio(ejercicio3);

        List<Ejercicio> listaEjercicios = new ArrayList<>();
        listaEjercicios.add(ejercicio1);
        listaEjercicios.add(ejercicio2);
        listaEjercicios.add(ejercicio3);

        Leccion leccion = crearLeccion();
        leccion.setEjercicios(listaEjercicios);
        this.sessionFactory.getCurrentSession().save(leccion);

        MvcResult result = this.mockMvc.perform(get("/ejercicio/2?leccion=" + leccion.getId())
                        .param("leccion", leccion.getId().toString())
                        .session(sessionMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        Ejercicio ejercicioEsperado = this.servicioEjercicio.obtenerEjercicio(ejercicio2.getId());

        assert modelAndView != null;

        assertThat(modelAndView.getViewName(), equalTo("ejercicio"));
        assertThat(modelAndView.getModel().get("ejercicio"), equalTo(ejercicioEsperado));
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

    private Leccion crearLeccion() {
        Leccion leccion = new Leccion();
        leccion.setTitulo("Leccion 1");
        List<Ejercicio> ejercicios = new ArrayList<>();
        Ejercicio ejercicio = crearEjercicio();
        this.sessionFactory.getCurrentSession().save(ejercicio);
        ejercicios.add(ejercicio);
        leccion.setEjercicios(ejercicios);
        return leccion;
    }
}
