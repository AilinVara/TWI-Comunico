package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.RepositorioLeccion;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateRepositorioTestConfig.class)
public class RepositorioLeccionImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioLeccion repositorioLeccion;

    @BeforeEach
    public void init(){
        this.repositorioLeccion = new RepositorioLeccionImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioLeccionCuandoGuardoUnaLeccionEntoncesLaEncuentroEnLaBaseDeDatos(){
        Leccion leccion = new Leccion();

        this.repositorioLeccion.guardar(leccion);
        Long leccionId = leccion.getId();

        String hql = "FROM Leccion WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", leccionId);
        Leccion leccionObtenida = (Leccion) query.getSingleResult();

        assertThat(leccionObtenida, equalTo(leccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaLeccionCuandoLaBuscoPorTituloEntoncesLaEncuentroEnLaBaseDeDatos(){
        Leccion leccion = new Leccion();
        leccion.setTitulo("Primer leccion");

        this.repositorioLeccion.guardar(leccion);

        Leccion leccionObtenida = this.repositorioLeccion.buscarPorTitulo("Primer leccion");

        assertThat(leccionObtenida, equalTo(leccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExistenDosEjerciciosEnUnaLeccionCuandoBuscoLaLeccionEntoncesObtengoLaLeccionConLosEjercicios(){
        Ejercicio ej1 = new Ejercicio();
        ej1.setConsigna("Consigna 1");
        Opcion op1 = new Opcion();
        op1.setDescripcion("Opcion 1");
        ej1.setOpcionCorrecta(op1);

        this.sessionFactory.getCurrentSession().save(op1);
        this.sessionFactory.getCurrentSession().save(ej1);

        Ejercicio ej2 = new Ejercicio();
        ej2.setConsigna("Consigna 2");
        Opcion op2 = new Opcion();
        op2.setDescripcion("Opcion 2");
        ej2.setOpcionCorrecta(op2);

        this.sessionFactory.getCurrentSession().save(op2);
        this.sessionFactory.getCurrentSession().save(ej2);

        List<Ejercicio> ejercicios = new ArrayList<>();
        ejercicios.add(ej1);
        ejercicios.add(ej2);

        Leccion leccion = new Leccion();
        leccion.setTitulo("Leccion con ejercicios");
        leccion.setEjercicios(ejercicios);

        this.sessionFactory.getCurrentSession().save(leccion);

        Leccion leccionObtenida = this.repositorioLeccion.buscarPorTitulo("Leccion con ejercicios");

        assertThat(leccionObtenida, equalTo(leccion));
        assertThat(leccionObtenida.getEjercicios(), equalTo(leccion.getEjercicios()));
    }
}
