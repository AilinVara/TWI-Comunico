package com.tallerwebi.integracion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioEjercicioTraduccionImpl;
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
public class ControladorEjercicioTraduccionTest {

    private ServicioEjercicio servicioEjercicio;
    private RepositorioEjercicioTraduccion repositorioEjercicioTraduccion;
    private ServicioVida servicioVida;
    private RepositorioVida repositorioVida;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private MockHttpSession sessionMock;


    @BeforeEach
    public void init(){
        this.repositorioEjercicioTraduccion = new RepositorioEjercicioTraduccionImpl(sessionFactory);
        this.servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicioTraduccion);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.sessionMock = new MockHttpSession();
        this.servicioVida = new ServicioVidaImpl(repositorioVida, repositorioUsuario);
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
        this.sessionFactory.getCurrentSession().save(vida);
        this.sessionFactory.getCurrentSession().update(usuario);

        Ejercicio ejercicioTraduccionResuelto = leccion.getEjercicios().get(0);

        EjercicioTraduccion ejercicioTraduccion = (EjercicioTraduccion) ejercicioTraduccionResuelto;

        MvcResult result = this.mockMvc.perform(post("/resolver/1?leccion=" + leccion.getId().toString())
                        .param("opcionSeleccionada", ejercicioTraduccion.getOpcionCorrecta().getId().toString())
                        .param("ejercicioId", ejercicioTraduccionResuelto.getId().toString())
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
    public void dadoQueExisteUnUsuarioLogueadoYUnaLeccionConTresEjerciciosCuandoNavegoALaRutaEjercicioYEnvioUnIdDeParametroReciboLaVistaEjercicioYElIndiceEnElModelo() throws Exception {
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        sessionMock.setAttribute("id", usuario.getId());

        Vida vida = new Vida();
        vida.setCantidadDeVidasActuales(5);
        usuario.setVida(vida);
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

        Leccion leccion = crearLeccion();
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
        List<Opcion> opcionesIncorrectas = new ArrayList<>();
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
        ejercicioTraduccions.add(ejercicioTraduccion);
        leccion.setEjercicios(ejercicioTraduccions);
        return leccion;
    }
}
