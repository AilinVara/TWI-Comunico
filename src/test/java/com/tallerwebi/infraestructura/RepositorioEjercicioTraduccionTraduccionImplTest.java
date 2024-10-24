package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioTraduccion;
import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.RepositorioEjercicioTraduccion;
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
public class RepositorioEjercicioTraduccionTraduccionImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEjercicioTraduccion repositorioEjercicioTraduccion;

    @BeforeEach
    public void init() {
        this.repositorioEjercicioTraduccion = new RepositorioEjercicioTraduccionImpl(sessionFactory);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnRepositorioEjercicioCuandoGuardoUnEjercicioEntoncesLoEncuentroEnLaBaseDeDatos() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        ejercicioTraduccion.setConsigna("A-E");

        this.repositorioEjercicioTraduccion.guardar(ejercicioTraduccion);

        String hql = "FROM EjercicioTraduccion where consigna = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "A-E");
        EjercicioTraduccion ejercicioTraduccionObtenido = (EjercicioTraduccion) query.getSingleResult();

        assertThat(ejercicioTraduccionObtenido, equalTo(ejercicioTraduccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioConOpcionCorrectaEnLaBaseDeDatosCuandoLoBuscoPorDescripcionDeLaOpcionCorrectaLoEncuentroEnLaBaseDeDatos() {
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        Opcion opcion = new Opcion("A");

        this.sessionFactory.getCurrentSession().save(opcion);

        ejercicioTraduccion.setOpcionCorrecta(opcion);

        this.repositorioEjercicioTraduccion.guardar(ejercicioTraduccion);

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

        this.repositorioEjercicioTraduccion.guardar(ejercicioTraduccion);

        EjercicioTraduccion ejercicioTraduccionObtenido = this.repositorioEjercicioTraduccion.buscarEjercicio(ejercicioTraduccion.getId());

        assertThat(ejercicioTraduccionObtenido, equalTo(ejercicioTraduccion));
    }

}
