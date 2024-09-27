package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.RepositorioLeccion;
import com.tallerwebi.infraestructura.config.HibernateRepositorioTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void dadoQueExisteUnRepositorioLeccionCuandoGuardoUnaOpcionEntoncesLaEncuentroEnLaBaseDeDatos(){
        Leccion leccion = new Leccion();

        this.repositorioLeccion.guardar(leccion);

        String hql = "FROM Leccion WHERE id = :id";
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", 1L);
        Leccion leccionObtenida = (Leccion) query.getSingleResult();

        assertThat(leccionObtenida, equalTo(leccion));
    }

}
