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


import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorEjercicioTest {

    private ServicioEjercicio servicioEjercicio;
    private RepositorioEjercicio repositorioEjercicio;
    //Commit
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

        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(5);
        usuario.setVida(vida);
        Experiencia experiencia = new Experiencia();
        experiencia.setCantidadExperiencia(400);
        usuario.setExperiencia(experiencia);

        this.sessionFactory.getCurrentSession().save(experiencia);
        this.sessionFactory.getCurrentSession().save(vida);
        this.sessionFactory.getCurrentSession().update(usuario);

        Ejercicio ejercicioTraduccionResuelto = leccion.getEjercicios().get(0);

        EjercicioTraduccion ejercicioTraduccion = (EjercicioTraduccion) ejercicioTraduccionResuelto;

        MvcResult result = this.mockMvc.perform(get("/ejercicio/3?leccion=" + leccion.getId())
                        .session(sessionMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assert modelAndView != null;

        assertThat("ejercicio", equalToIgnoringCase(Objects.requireNonNull(modelAndView.getViewName())));
        assertThat(false, is(modelAndView.getModel().isEmpty()));
    }



    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConTresEjerciciosCuandoNavegoALaRutaEjercicioYEnvioUnIdDeParametroReciboLaVistaEjercicioYElIndiceEnElModelo() throws Exception {
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        sessionMock.setAttribute("id", usuario.getId());

        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(5);
        usuario.setVida(vida);
        Experiencia experiencia = new Experiencia();
        experiencia.setCantidadExperiencia(400);
        usuario.setExperiencia(experiencia);

        this.sessionFactory.getCurrentSession().save(experiencia);
        this.sessionFactory.getCurrentSession().save(vida);
        this.sessionFactory.getCurrentSession().update(usuario);

        EjercicioTraduccion ejercicioTraduccion1 = crearEjercicio();
        ejercicioTraduccion1.setConsigna("Consigna 1");

        EjercicioTraduccion ejercicioTraduccion2 = crearEjercicio();
        ejercicioTraduccion2.setConsigna("Consigna 2");

        EjercicioTraduccion ejercicioTraduccion3 = crearEjercicio();
        ejercicioTraduccion3.setConsigna("Consigna 3");

        this.servicioEjercicio.guardarEjercicio(ejercicioTraduccion1);
        this.servicioEjercicio.guardarEjercicio(ejercicioTraduccion2);
        this.servicioEjercicio.guardarEjercicio(ejercicioTraduccion3);

        List<Ejercicio> listaEjercicioTraduccions = new ArrayList<>();
        listaEjercicioTraduccions.add(ejercicioTraduccion1);
        listaEjercicioTraduccions.add(ejercicioTraduccion2);
        listaEjercicioTraduccions.add(ejercicioTraduccion3);

        Leccion leccion = new Leccion();
        leccion.setEjercicios(listaEjercicioTraduccions);
        this.sessionFactory.getCurrentSession().save(leccion);

        MvcResult result = this.mockMvc.perform(get("/ejercicio/3?leccion=" + leccion.getId())
                        .param("leccion", leccion.getId().toString())
                        .session(sessionMock))
                .andExpect(status().isOk())
                .andReturn();

        ModelAndView modelAndView = result.getModelAndView();

        assert modelAndView != null;

        assertThat(modelAndView.getViewName(), equalTo("ejercicio"));
        assertThat(modelAndView.getModel().get("indice"), equalTo(3));
    }


    private EjercicioTraduccion crearEjercicio() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        ejercicioTraduccion.setConsigna("Consigna");
        Opcion opcionCorrecta = new Opcion("A");
        Opcion opcionIncorrecta1 = new Opcion("B");
        Opcion opcionIncorrecta2 = new Opcion("C");
        this.sessionFactory.getCurrentSession().save(opcionCorrecta);
        this.sessionFactory.getCurrentSession().save(opcionIncorrecta1);
        this.sessionFactory.getCurrentSession().save(opcionIncorrecta2);
        Set<Opcion> opcionesIncorrectas = new HashSet<>();
        opcionesIncorrectas.add(opcionIncorrecta1);
        opcionesIncorrectas.add(opcionIncorrecta2);
        ejercicioTraduccion.setOpcionCorrecta(opcionCorrecta);
        ejercicioTraduccion.setOpcionesIncorrectas(opcionesIncorrectas);
        return ejercicioTraduccion;
    }

    private Leccion crearLeccion() {
        Leccion leccion = new Leccion();
        leccion.setTitulo("Leccion 1");
        List<Ejercicio> ejercicioTraduccions = new ArrayList<>();
        EjercicioTraduccion ejercicioTraduccion = crearEjercicio();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion);
        EjercicioTraduccion ejercicioTraduccion2 = crearEjercicio();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion2);
        EjercicioTraduccion ejercicioTraduccion3 = crearEjercicio();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion3);
        ejercicioTraduccions.add(ejercicioTraduccion);
        ejercicioTraduccions.add(ejercicioTraduccion2);
        ejercicioTraduccions.add(ejercicioTraduccion3);
        leccion.setEjercicios(ejercicioTraduccions);
        return leccion;
    }
}
