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

import java.util.List;

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

    public ProgresoLeccion crearProgresoLeccion(){
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        Leccion leccion = new Leccion();
        this.sessionFactory.getCurrentSession().save(leccion);
        EjercicioTraduccion ejercicioTraduccion = new EjercicioTraduccion();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion);
        return new ProgresoLeccion(usuario, leccion, ejercicioTraduccion);
    }


    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnUsuarioUnaLeccionYUnEjercicioPuedoUsarlosParaGuardarUnProgresoLeccionEnLaBaseDeDatos(){

        ProgresoLeccion progresoLeccion = crearProgresoLeccion();

        this.repositorioProgresoLeccion.guardar(progresoLeccion);

        String hql = "SELECT pg FROM ProgresoLeccion pg JOIN Usuario u ON pg.usuario = u.id JOIN Leccion l on pg.leccion = l.id JOIN Ejercicio e ON pg.ejercicio = e.id WHERE u.id = :uid AND l.id = :lid AND e.id = :eid";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("uid", progresoLeccion.getUsuario().getId());
        query.setParameter("lid", progresoLeccion.getLeccion().getId());
        query.setParameter("eid", progresoLeccion.getEjercicio().getId());
        ProgresoLeccion progresoLeccionRecibido = (ProgresoLeccion) query.getSingleResult();

        assertThat(progresoLeccionRecibido, equalTo(progresoLeccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnProgresoLeccionCuandoLoBuscoPorIdsEntoncesLoEncuentroEnLaBaseDeDatos() {
        ProgresoLeccion progresoLeccion = crearProgresoLeccion();
        this.repositorioProgresoLeccion.guardar(progresoLeccion);

        Long leccionId = progresoLeccion.getLeccion().getId();
        Long usuarioId = progresoLeccion.getUsuario().getId();
        Long ejercicioId = progresoLeccion.getEjercicio().getId();

        ProgresoLeccion progresoRecibido = this.repositorioProgresoLeccion.buscarPorIds(leccionId,usuarioId,ejercicioId);

        assertThat(progresoRecibido, equalTo(progresoLeccion));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisten3ProgresosLeccionDeUnUsuarioEnUnaLeccionCuandoLosBuscoPorUsuarioIdYLeccionIdEntoncesLosEncuentroEnLaBaseDeDatos() {
        Leccion leccion = new Leccion();
        this.sessionFactory.getCurrentSession().save(leccion);
        Usuario usuario = new Usuario();
        this.sessionFactory.getCurrentSession().save(usuario);
        EjercicioTraduccion ejercicioTraduccion1 = new EjercicioTraduccion();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion1);
        EjercicioTraduccion ejercicioTraduccion2 = new EjercicioTraduccion();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion2);
        EjercicioTraduccion ejercicioTraduccion3 = new EjercicioTraduccion();
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion3);

        ProgresoLeccion progreso1 = new ProgresoLeccion(usuario, leccion, ejercicioTraduccion1);
        ProgresoLeccion progreso2 = new ProgresoLeccion(usuario, leccion, ejercicioTraduccion2);
        ProgresoLeccion progreso3 = new ProgresoLeccion(usuario, leccion, ejercicioTraduccion3);

        this.repositorioProgresoLeccion.guardar(progreso1);
        this.repositorioProgresoLeccion.guardar(progreso2);
        this.repositorioProgresoLeccion.guardar(progreso3);

        List<ProgresoLeccion> progresosRecibidos = this.repositorioProgresoLeccion.buscarPorUsuarioIdYLeccionId(usuario.getId(), leccion.getId());

        assertThat(progresosRecibidos.size(), equalTo(3));
        assertThat(progresosRecibidos.contains(progreso1), equalTo(true));
        assertThat(progresosRecibidos.contains(progreso2), equalTo(true));
        assertThat(progresosRecibidos.contains(progreso3), equalTo(true));
    }

    @Test
    @Transactional
    @Rollback
    public void dadoQueExisteUnProgresoLeccionCuandoLoActualizoEntoncesSeReflejanLosCambios(){
        ProgresoLeccion progreso = crearProgresoLeccion();
        this.repositorioProgresoLeccion.guardar(progreso);

        ProgresoLeccion progresoAntes = this.repositorioProgresoLeccion.buscarPorIds(progreso.getLeccion().getId(), progreso.getUsuario().getId(), progreso.getEjercicio().getId());

        assertThat(progresoAntes.getCompleto(), equalTo(false));

        progresoAntes.setCompleto(true);

        this.repositorioProgresoLeccion.actualizar(progresoAntes);

        ProgresoLeccion progresoDespues = this.repositorioProgresoLeccion.buscarPorIds(progresoAntes.getLeccion().getId(), progresoAntes.getUsuario().getId(), progresoAntes.getEjercicio().getId());

        assertThat(progresoDespues.getCompleto(), equalTo(true));
    }
}
