package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Experiencia;
import com.tallerwebi.dominio.RepositorioExperiencia;
import com.tallerwebi.dominio.RepositorioVida;
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
public class RepositorioExperienciaImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioExperiencia repositorioExperiencia;

    @BeforeEach
    public void init(){
        this.repositorioExperiencia = new RepositorioExperienciaImpl(sessionFactory);
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueExisteUnRepositorioExperienciaCuandoGuardoUnaExperienciaEntoncesLaEncuentroEnLaBaseDeDatos() {

        Experiencia experiencia = new Experiencia();
        this.repositorioExperiencia.guardarExperiencia(experiencia);
        String hql = "FROM Experiencia where id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id",experiencia.getId());
        Experiencia expObtenida = (Experiencia) query.getSingleResult();

        assertThat(expObtenida.getCantidadExperiencia(), equalTo(experiencia.getCantidadExperiencia()));
        assertThat(expObtenida, equalTo(experiencia));
    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueExisteUnaExperienciaEntoncesCuandoLaGuardoLuegoLaBuscoEntoncesLaEncuentroEnLaBaseDeDatos() {
    Experiencia experiencia = new Experiencia();
    this.repositorioExperiencia.guardarExperiencia(experiencia);
    Long experienciaId = experiencia.getId();
    Experiencia experienciaObtenida = this.repositorioExperiencia.buscarExperienciaPorId(experienciaId);
    assertThat(experienciaObtenida, equalTo(experiencia));
    }
    @Test
    @Rollback
    @Transactional
    public void dadoQueExisteUnaExperienciaEntoncesCuandoLaActualizoEntoncesLaEncuentroEnLaBaseDeDatosActualizada() {
        Experiencia experiencia = new Experiencia();
        this.repositorioExperiencia.guardarExperiencia(experiencia);
        Long experienciaId = experiencia.getId();
        Experiencia experienciaAnterior = this.repositorioExperiencia.buscarExperienciaPorId(experienciaId);
        assertThat(experienciaAnterior.getCantidadExperiencia(),equalTo(0));
        experiencia.setCantidadExperiencia(100);
        this.repositorioExperiencia.actualizarExperiencia(experiencia);
        Experiencia experienciaPosterior = this.repositorioExperiencia.buscarExperienciaPorId(experienciaId);
        assertThat(experienciaAnterior.getCantidadExperiencia(),equalTo(100));
    }
    }
