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
public class RepositorioVidaImplTest {

    @Autowired
    private SessionFactory sessionFactory;
    private RepositorioVida repositorioVida;



    @BeforeEach
    public void init() {
        this.repositorioVida = new RepositorioVidaImpl(sessionFactory);
    }

    @Test
    @Rollback
    @Transactional
    public void dadoQueExisteUnRepositorioVidaCuandoGuardoUnaVidaEntoncesLaEncuentroEnLaBaseDeDatos() {

        int cantidadDeVidasActuales = 5;
        Vida vida = new Vida(cantidadDeVidasActuales);
        this.repositorioVida.guardarUnaVida(vida);

        String hql = "FROM Vida where id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", vida.getId());
        Vida vidaObtenida = (Vida) query.getSingleResult();

        assertThat(vidaObtenida.getCantidadDeVidasActuales(), equalTo(vida.getCantidadDeVidasActuales()));
        assertThat(vidaObtenida, equalTo(vida));

    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaVidaEntoncesCuandoLaGuardoLuegoLaBuscoEntoncesLaEncuentroEnLaBaseDeDatos() {
        Vida vida = new Vida();

        this.repositorioVida.guardarUnaVida(vida);

        Long vidaId = vida.getId();

        Vida vidaObtenida = this.repositorioVida.buscarUnaVidaPorId(vidaId);

        assertThat(vidaObtenida, equalTo(vida));

    }



    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnaVidaEntoncesCuandoLaActualizoEntoncesLaEncuentroEnLaBaseDeDatosActualizada() {
        Vida vida = new Vida();
        this.repositorioVida.guardarUnaVida(vida);
        Vida vidaAnterior = this.repositorioVida.buscarUnaVidaPorId(vida.getId());
        assertThat(vidaAnterior.getCantidadDeVidasActuales(),equalTo(5));
        vida.setCantidadDeVidasActuales(4);
        this.repositorioVida.actualizarVida(vida);
        Vida vidaPosterior = this.repositorioVida.buscarUnaVidaPorId(vida.getId());

        assertThat(vidaPosterior.getCantidadDeVidasActuales(),equalTo(4));

    }

}


