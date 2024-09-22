package com.tallerwebi.integracion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.ServicioEjercicio;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.ControladorEjercicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.mockito.Mockito.mock;
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class ControladorEjercicioTest {

    private ControladorEjercicio controladorEjercicio;
    private Ejercicio ejercicioMock;
    private ServicioEjercicio servicioEjercicioMock;


    @BeforeEach
    public void init(){
        ejercicioMock = mock(Ejercicio.class);
        controladorEjercicio = new ControladorEjercicio(servicioEjercicioMock);
    }

}
