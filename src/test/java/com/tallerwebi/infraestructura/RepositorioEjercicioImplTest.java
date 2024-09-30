package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.RepositorioEjercicio;
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
    public void dadoQueExisteUnRepositorioEjercicioCuandoGuardoUnEjercicioEntoncesLoEncuentroEnLaBaseDeDatos(){
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setConsigna("A-E");

        this.repositorioEjercicio.guardar(ejercicio);

        String hql = "FROM Ejercicio where consigna = :nombre";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("nombre", "A-E");
        Ejercicio ejercicioObtenido = (Ejercicio) query.getSingleResult();

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioConOpcionCorrectaEnLaBaseDeDatosCuandoLoBuscoPorDescripcionDeLaOpcionCorrectaLoEncuentroEnLaBaseDeDatos(){
        Ejercicio ejercicio = new Ejercicio();
        Opcion opcion = new Opcion("A");

        this.sessionFactory.getCurrentSession().save(opcion);

        ejercicio.setOpcionCorrecta(opcion);

        this.repositorioEjercicio.guardar(ejercicio);

        String hql = "SELECT e FROM Ejercicio e JOIN Opcion o ON e.opcionCorrecta.id = o.id WHERE o.descripcion = :descripcion";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("descripcion", opcion.getDescripcion());
        Ejercicio ejercicioObtenido = (Ejercicio) query.getSingleResult();

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioEnLaBaseDeDatosLoEncuentroPorSuId(){
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setConsigna("A-E");

        this.repositorioEjercicio.guardar(ejercicio);

        Ejercicio ejercicioObtenido = this.repositorioEjercicio.buscarEjercicio(ejercicio.getId());

        assertThat(ejercicioObtenido, equalTo(ejercicio));
    }

}
