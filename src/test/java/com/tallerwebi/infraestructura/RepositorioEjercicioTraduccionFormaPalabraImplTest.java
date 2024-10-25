package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioFormaPalabra;
import com.tallerwebi.dominio.RepositorioEjercicioFormaPalabra;
import com.tallerwebi.infraestructura.config.HibernateRepositorioTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateRepositorioTestConfig.class)
public class RepositorioEjercicioTraduccionFormaPalabraImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEjercicioFormaPalabra repositorioEjercicioFormaPalabra;

    @BeforeEach
    public void init() {
        this.repositorioEjercicioFormaPalabra = new RepositorioEjercicioFormaPalabraImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEjercicioFormaPalabraCuandoGuardoUnEjercicioEntoncesLoEncuentroEnLaBaseDeDatos(){
        EjercicioFormaPalabra ejercicio = new EjercicioFormaPalabra();
        ejercicio.setRespuestaCorrecta("GATO");

        this.repositorioEjercicioFormaPalabra.guardar(ejercicio);

        String hql = "FROM EjercicioFormaPalabra where respuestaCorrecta = :respuesta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("respuesta", "GATO");
        EjercicioFormaPalabra ejercicioObtenido = (EjercicioFormaPalabra) query.getSingleResult();

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioFormaPalabraEnLaBaseDeDatosLoEncuentroPorSuId(){
        EjercicioFormaPalabra ejercicio = new EjercicioFormaPalabra();
        ejercicio.setLetras("PERRO");

        this.repositorioEjercicioFormaPalabra.guardar(ejercicio);

        EjercicioFormaPalabra ejercicioObtenido = this.repositorioEjercicioFormaPalabra.buscar(ejercicio.getId());

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

}

