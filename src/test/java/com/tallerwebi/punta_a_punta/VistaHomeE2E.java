package com.tallerwebi.punta_a_punta;

import com.microsoft.playwright.*;
import com.tallerwebi.punta_a_punta.vistas.VistaHome;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

public class VistaHomeE2E {

    static Playwright playwright;
    static Browser browser;
    BrowserContext context;
    VistaHome vistaHome;

    @BeforeAll
    static void abrirNavegador() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch();
        // browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
    }

    @AfterAll
    static void cerrarNavegador() {
        playwright.close();
    }

    @BeforeEach
    void crearContextoYPagina() {
        context = browser.newContext();
        Page page = context.newPage();

        // Simulación de inicio de sesión antes de navegar al Home
        page.navigate("localhost:8080/spring/login");
        page.locator("input[name='email']").type("test@unlam.edu.ar");
        page.locator("input[name='clave']").type("test");
        page.locator("button[name='iniciarSesion']").click();

        page.navigate("localhost:8080/inicio");

        vistaHome = new VistaHome(page);
    }

    @AfterEach
    void cerrarContexto() {
        context.close();
    }


    @Test
    void deberiaMostrarLosElementosCorrectosEnElHeader() {
        String inicio = vistaHome.obtenerTextoInicio();
        assertThat("Inicio", equalToIgnoringCase(inicio));

        String senias = vistaHome.obtenerTextoSenias();
        assertThat("Lengua de Señas", equalToIgnoringCase(senias));

        String braille = vistaHome.obtenerTextoBraille();
        assertThat("Braille", equalToIgnoringCase(braille));

        String suscripcion = vistaHome.obtenerTextoSuscripcion();
        assertThat("Suscripciones", equalToIgnoringCase(suscripcion));
    }

}
