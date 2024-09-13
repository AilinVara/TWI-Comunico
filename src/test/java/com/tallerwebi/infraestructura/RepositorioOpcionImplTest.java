package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.RepositorioOpcion;
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
public class RepositorioOpcionImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioOpcion repositorioOpcion;

    @BeforeEach
    public void init(){
        this.repositorioOpcion = new RepositorioOpcionImpl(sessionFactory);
    }

    @Test
    @Transactional
    public void dadoQueExisteUnOpcionRepositorioCuandoGuardoUnaOpcionEntoncesLaEncuentroEnLaBaseDeDatos(){
        Opcion opcion = new Opcion("A");

        this.repositorioOpcion.guardar(opcion);

        String hql = "FROM Opcion WHERE descripcion = :descripcion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("descripcion", "A");
        Opcion opcionObtenida = (Opcion) query.getSingleResult();

        assertThat(opcionObtenida, equalTo(opcion));
    }
}
