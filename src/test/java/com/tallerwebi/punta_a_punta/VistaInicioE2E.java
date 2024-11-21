package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.VistaInicio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaInicioE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaInicio vistaInicio;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        //browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();
        vistaInicio = new VistaInicio(page);
    }

    @Test
    void debeDecirIniciarSesionYRegistrarseEnLosBotonesDelIndice(){
        String botonIniciarSesion = vistaInicio.obtenerTextoDelBotonLogIn();
        String botonRegistrarse = vistaInicio.obtenerTextoDelBotonRegistrarse();

        assertThat("Iniciar Sesion", equalToIgnoringCase(botonIniciarSesion));
        assertThat("Registrarse", equalToIgnoringCase(botonRegistrarse));
    }

    @Test
    void deberiaNavegarAlRegistroSiApretaEnElBotonDeRegistrarse(){
        vistaInicio.darClickEnRegistrarse();

        String url = vistaInicio.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/nuevo-usuario"));
    }

    @Test
    void deberiaNavegarAlInicioSesionSiApretaEnElBotonDeIniciarSesion(){
        vistaInicio.darClickEnIniciarSesion();

        String url = vistaInicio.obtenerURLActual();
        assertThat(url, containsStringIgnoringCase("/login"));
    }
}
