package com.tallerwebi.integracion;

import com.tallerwebi.dominio.Vida;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
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
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorLoginTest {

	private Usuario usuarioMock;
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;
	private MockHttpSession sessionMock;


	@BeforeEach
	public void init(){
		usuarioMock = mock(Usuario.class);
		when(usuarioMock.getEmail()).thenReturn("dami@unlam.com");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}


	@Test
	public void debeRetornarLaPaginaLoginCuandoSeNavegaALLogin() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/login"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
        assert modelAndView != null;
        assertThat(modelAndView.getViewName(), equalToIgnoringCase("login"));
		assertThat(modelAndView.getModel().get("datosLogin").toString(),  containsString("com.tallerwebi.presentacion.DatosLogin"));
	}

	@Test
	public void debeRetornarLaPaginaSeniasCuandoSeNavegaASenias() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/senias"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assert modelAndView != null;
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("senias"));
		assertThat(false,  is(modelAndView.getModel().isEmpty()));
	}

	@Test
	public void debeRetornarLaPaginaBrailleCuandoSeNavegaABraille() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/braille"))
				.andExpect(status().isOk())
				.andReturn();

		ModelAndView modelAndView = result.getModelAndView();
		assert modelAndView != null;
		assertThat(modelAndView.getViewName(), equalToIgnoringCase("braille"));
	}

}
