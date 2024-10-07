package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Leccion;
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
    public void dadoQueExisteUnaLeccionCuandoLaBuscoPorIdEntoncesLaEncuentroEnLaBaseDeDatos(){
        Leccion leccion = new Leccion();

        this.repositorioLeccion.guardar(leccion);

        Leccion leccionObtenida = this.repositorioLeccion.buscarPorId(leccion.getId());

        assertThat(leccionObtenida, equalTo(leccion));
    }

}
