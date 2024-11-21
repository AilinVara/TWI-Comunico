package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.tallerwebi.punta_a_punta.vistas.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaEjercicioE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    Page page;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }

    @Test
    void deberiaLlegarALaVistaEjercicioTrasNavegar() {
        VistaLogin vistaLogin = new VistaLogin(page);
        vistaLogin.escribirEMAIL("test@unlam.edu.ar");
        vistaLogin.escribirClave("test");
        vistaLogin.darClickEnIniciarSesion();

        VistaHome vistaHome = new VistaHome(page);
        String tituloHome = vistaHome.obtenerTextoDelTitulo();
        assertThat("¡Bienvenido a Comunico!", equalToIgnoringCase(tituloHome));
        vistaHome.darClickEnElBotonBraille();

        VistaMenuBraille vistaMenuBraille = new VistaMenuBraille(page);
        String leccionTraduccion = vistaMenuBraille.obtenerTextoDeLaLeccionTraduccion();
        assertThat("Reconocé letras", equalToIgnoringCase(leccionTraduccion));
        vistaMenuBraille.darClickEnElElementoTraduccion();

        VistaMapaLecciones vistaMapaLecciones = new VistaMapaLecciones(page);
        String textoPrimeraLeccion = vistaMapaLecciones.obtenerTextoDeLaPrimerLeccion();
        assertThat("Lección 1", equalToIgnoringCase(textoPrimeraLeccion));
        vistaMapaLecciones.darClickEnLaLeccion(1);

        VistaEjercicio vistaEjercicio = new VistaEjercicio(page);
        String tituloEjercicio = vistaEjercicio.obtenerTextoDelTitulo();
        assertThat("Ejercicio 1:", equalToIgnoringCase(tituloEjercicio));
    }
}
