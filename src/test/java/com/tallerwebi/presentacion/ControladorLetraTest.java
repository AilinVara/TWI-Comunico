//package com.tallerwebi.presentacion;
//
//import com.tallerwebi.dominio.Letra;
//import com.tallerwebi.dominio.ServicioLetra;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ControladorLetraTest {
//
//    private ControladorLetra controladorLetra;
//    private ServicioLetra servicioLetraMock;
//
//    @BeforeEach
//    public void init() {
//        // Crear el mock del servicio
//        servicioLetraMock = mock(ServicioLetra.class);
//        controladorLetra = new ControladorLetra(servicioLetraMock);
//    }
//
//    @Test
//    public void cuandoNavegoASeniasAlfabetoEntoncesReciboLaVistaAlfabetoYUnaListaConLetrasEnElModelo(){
//        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");
//
//        when(servicioLetraMock.buscarTodasLasLetras()).thenReturn(List.of(letra));
//
//        ModelAndView modelAndView = this.controladorLetra.alfabetoSenias("");
//
//        assertNotNull(modelAndView);
//        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
//        assertThat(modelAndView.getModel().size(), equalTo(1) );
//        assertThat(modelAndView.getModel().get("letrassenias"), equalTo(List.of(letra)));
//    }
//
//    @Test
//    public void cuandoNavegoABrailleAlfabetoEntoncesReciboLaVistaAlfabetoYUnaListaConLetrasEnElModelo(){
//        Letra letraA = new Letra( "A", "senias-a.png","braille-a.png");
//        Letra letraB = new Letra( "B", "senias-b.png","braille-b.png");
//        Letra letraC= new Letra( "C", "senias-c.png","braille-c.png");
//
//        List<Letra> letras = new ArrayList<>();
//        letras.add(letraA);
//        letras.add(letraB);
//        letras.add(letraC);
//
//        when(servicioLetraMock.buscarTodasLasLetras()).thenReturn(letras);
//
//        ModelAndView modelAndView = this.controladorLetra.alfabetoBraille("");
//
//        assertNotNull(modelAndView);
//        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
//        assertThat(modelAndView.getModel().size(), equalTo(1) );
//        assertThat(modelAndView.getModel().get("letrasbraille"), equalTo(letras));
//    }
//
//    @Test
//    public void cuandoNavegoASeniasAlfabetoYEnvioUnaLetraDeParametroEntoncesReciboLaVistaAlfabetoYLaLetraEnElModelo() {
//        // Crear una letra de ejemplo
//        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");
//
//        // Definir comportamiento del mock
//        when(servicioLetraMock.buscarPorNombre("A")).thenReturn(letra);
//
//        // Llamar al controlador
//        ModelAndView modelAndView = controladorLetra.alfabetoSenias("A");
//
//        // Verificar el resultado
//        assertNotNull(modelAndView);
//        assertThat(modelAndView.getModel().size(), equalTo(1) );
//        assertThat(modelAndView.getModel().get("letrassenias"), equalTo(List.of(letra)));
//        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
//    }
//
//    @Test
//    public void cuandoNavegoABrailleAlfabetoYEnvioUnaLetraDeParametroEntoncesReciboLaVistaAlfabetoYLaLetraEnElModelo() {
//        // Crear una letra de ejemplo
//        Letra letra = new Letra( "A", "senias-a.png","braille-a.png");
//
//        // Definir comportamiento del mock
//        when(servicioLetraMock.buscarPorNombre("A")).thenReturn(letra);
//
//        // Llamar al controlador
//        ModelAndView modelAndView = controladorLetra.alfabetoBraille("A");
//
//        // Verificar el resultado
//        assertNotNull(modelAndView);
//        assertThat(modelAndView.getModel().size(), equalTo(1));
//        assertThat(modelAndView.getModel().get("letrasbraille"), equalTo(List.of(letra)));
//        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
//    }
//
//    @Test
//    public void dadoQueExistenVariasLetrasCuandoNavegoABrailleAlfabetoYEnvioUnParametroInvalidoEntoncesReciboLaVistaAlfabetoYTodasLasLetrasEnElModelo() {
//        Letra letraA = new Letra( "A", "senias-a.png","braille-a.png");
//        Letra letraB = new Letra( "B", "senias-b.png","braille-b.png");
//        Letra letraC= new Letra( "C", "senias-c.png","braille-c.png");
//
//        List<Letra> letras = new ArrayList<>();
//        letras.add(letraA);
//        letras.add(letraB);
//        letras.add(letraC);
//
//        // Definir comportamiento del mock
//        when(servicioLetraMock.buscarPorNombre("PRUEBAINVALIDA")).thenReturn(null);
//        when(servicioLetraMock.buscarTodasLasLetras()).thenReturn(letras);
//
//        // Llamar al controlador
//        ModelAndView modelAndView = controladorLetra.alfabetoBraille("PRUEBAINVALIDA");
//
//        // Verificar el resultado
//        assertNotNull(modelAndView);
//        assertThat(modelAndView.getModel().size(), equalTo(1));
//        assertThat(modelAndView.getModel().get("letrasbraille"), equalTo(letras));
//        assertThat(modelAndView.getViewName(), equalTo("alfabeto"));
//    }
//}
