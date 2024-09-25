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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = HibernateRepositorioTestConfig.class)
public class RepositorioEjercicioImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioEjercicio repositorioEjercicio;
    private Opcion opcionMock;

    @BeforeEach
    public void init() {
        this.repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
        opcionMock = mock(Opcion.class);
        when(opcionMock.getId()).thenReturn(1L);
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnEjercicioEnLaBaseDeDatosCuandoLeAgregoLaOpcionCorrectaLoEncuentroEnLaBaseDeDatos(){
        Ejercicio ejercicio = new Ejercicio();
        ejercicio.setOpcionCorrecta(opcionMock);

        this.repositorioEjercicio.guardar(ejercicio);

        String hql = "FROM Ejercicio where opcionCorrecta_id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", opcionMock.getId());
        Ejercicio ejercicioObtenido = (Ejercicio) query.getSingleResult();

        assertThat(ejercicioObtenido, equalTo(ejercicio));
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



}
