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
public class RepositorioProgresoLeccionTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioProgresoLeccion repositorioProgresoLeccion;

    @BeforeEach
    public void init() {
        this.repositorioProgresoLeccion = new RepositorioImplProgresoLeccion(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnUsuarioUnaLeccionYUnEjercicioPuedoUsarlosParaGuardarUnProgresoLeccionEnLaBaseDeDatos(){
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        Leccion leccion = new Leccion();
        this.sessionFactory.getCurrentSession().save(leccion);
        Ejercicio ejercicio = new Ejercicio();
        this.sessionFactory.getCurrentSession().save(ejercicio);

        ProgresoLeccion progresoLeccion = new ProgresoLeccion(usuario, leccion, ejercicio);

        this.repositorioProgresoLeccion.guardar(progresoLeccion);

        String hql = "SELECT pg FROM ProgresoLeccion pg JOIN Usuario u ON pg.usuario = u.id JOIN Leccion l on pg.leccion = l.id JOIN Ejercicio e ON pg.ejercicio = e.id WHERE u.id = :uid AND l.id = :lid AND e.id = :eid";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("uid", usuario.getId());
        query.setParameter("lid", leccion.getId());
        query.setParameter("eid", ejercicio.getId());
        ProgresoLeccion progresoLeccionRecibido = (ProgresoLeccion) query.getSingleResult();

        assertThat(progresoLeccionRecibido, equalTo(progresoLeccion));
    }

}
