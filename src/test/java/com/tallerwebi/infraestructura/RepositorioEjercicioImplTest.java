package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
public class RepositorioEjercicioImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEjercicio repositorioEjercicio;

    @BeforeEach
    public void init() {
        this.repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEjercicioCuandoGuardoUnEjercicioTraduccionEntoncesLoEncuentroEnLaBaseDeDatos() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        ejercicioTraduccion.setConsigna("A-E");

        this.repositorioEjercicio.guardar(ejercicioTraduccion);

        String hql = "FROM EjercicioTraduccion where consigna = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "A-E");
        EjercicioTraduccion ejercicioTraduccionObtenido = (EjercicioTraduccion) query.getSingleResult();

        assertThat(ejercicioTraduccionObtenido, equalTo(ejercicioTraduccion));
    }
    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEjercicioCuandoGuardoUnEjercicioMatrizEntoncesLoEncuentroEnLaBaseDeDatos() {
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();
        ejercicioMatriz.setPuntos("100000");

        this.repositorioEjercicio.guardar(ejercicioMatriz);

        String hql = "FROM EjercicioMatriz where puntos = :puntos";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("puntos", "100000");
        EjercicioMatriz ejercicioMatrizObtenido = (EjercicioMatriz) query.getSingleResult();

        assertThat(ejercicioMatrizObtenido, equalTo(ejercicioMatriz));
    }
    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEjercicioCuandoGuardoUnEjercicioFormaPalabraEntoncesLoEncuentroEnLaBaseDeDatos() {
        EjercicioFormaPalabra ejercicioFormaPalabra = new EjercicioFormaPalabra();
        ejercicioFormaPalabra.setRespuestaCorrecta("GATO");

        this.repositorioEjercicio.guardar(ejercicioFormaPalabra);

        String hql = "FROM EjercicioFormaPalabra where respuestaCorrecta = :respuesta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("respuesta", "GATO");
        EjercicioFormaPalabra ejercicioFormaPalabraObtenido = (EjercicioFormaPalabra) query.getSingleResult();

        assertThat(ejercicioFormaPalabraObtenido, equalTo(ejercicioFormaPalabra));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioConOpcionCorrectaEnLaBaseDeDatosCuandoLoBuscoPorDescripcionDeLaOpcionCorrectaLoEncuentroEnLaBaseDeDatos() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        Opcion opcion = new Opcion("A");

        this.sessionFactory.getCurrentSession().save(opcion);

        ejercicioTraduccion.setOpcionCorrecta(opcion);

        this.repositorioEjercicio.guardar(ejercicioTraduccion);

        String hql = "SELECT e FROM EjercicioTraduccion e JOIN Opcion o ON e.opcionCorrecta.id = o.id WHERE o.descripcion = :descripcion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("descripcion", opcion.getDescripcion());
        EjercicioTraduccion ejercicioTraduccionObtenido = (EjercicioTraduccion) query.getSingleResult();

        assertThat(ejercicioTraduccionObtenido, equalTo(ejercicioTraduccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioEnLaBaseDeDatosLoEncuentroPorSuId() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        ejercicioTraduccion.setConsigna("A-E");

        this.repositorioEjercicio.guardar(ejercicioTraduccion);

        EjercicioTraduccion ejercicioTraduccionObtenido = (EjercicioTraduccion) this.repositorioEjercicio.buscarEjercicio(ejercicioTraduccion.getId());

        assertThat(ejercicioTraduccionObtenido, equalTo(ejercicioTraduccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioFormaPalabraEnLaBaseDeDatosLoEncuentroPorSuId(){
        EjercicioFormaPalabra ejercicio = new EjercicioFormaPalabra();
        ejercicio.setLetras("PERRO");

        this.repositorioEjercicio.guardar(ejercicio);

        EjercicioFormaPalabra ejercicioObtenido = (EjercicioFormaPalabra) this.repositorioEjercicio.buscarEjercicio(ejercicio.getId());

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaMatrizEnLaBaseDeDatosLaEncuentroPorSuId(){
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();

        this.repositorioEjercicio.guardar(ejercicioMatriz);

        EjercicioMatriz ejercicioMatrizObtenida = (EjercicioMatriz) this.repositorioEjercicio.buscarEjercicio(ejercicioMatriz.getId());

        assertThat(ejercicioMatrizObtenida, equalTo(ejercicioMatriz));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenTresMatricesEnLaBaseDeDatosEncuentroUnaPorSuId(){
        EjercicioMatriz ejercicioMatriz = new EjercicioMatriz();
        EjercicioMatriz ejercicioMatriz2 = new EjercicioMatriz();
        EjercicioMatriz ejercicioMatriz3 = new EjercicioMatriz();

        this.repositorioEjercicio.guardar(ejercicioMatriz);
        this.repositorioEjercicio.guardar(ejercicioMatriz2);
        this.repositorioEjercicio.guardar(ejercicioMatriz3);

        EjercicioMatriz ejercicioMatrizObtenida = (EjercicioMatriz) this.repositorioEjercicio.buscarEjercicio(ejercicioMatriz2.getId());

        assertThat(ejercicioMatrizObtenida, equalTo(ejercicioMatriz2));
    }

}
