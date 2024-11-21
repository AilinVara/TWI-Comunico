package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.ProgresoLeccion;
import com.tallerwebi.dominio.RepositorioProgresoLeccion;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RepositorioImplProgresoLeccion implements RepositorioProgresoLeccion {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioImplProgresoLeccion(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ProgresoLeccion progresoLeccion) {
        this.sessionFactory.getCurrentSession().save(progresoLeccion);
    }

    @Override
    public ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId) {
        return (ProgresoLeccion) this.sessionFactory.getCurrentSession().createCriteria(ProgresoLeccion.class)
                .add(Restrictions.eq("leccion.id", leccionId))
                .add(Restrictions.eq("usuario.id", usuarioId))
                .add(Restrictions.eq("ejercicio.id", ejercicioId))
                .uniqueResult();
    }

    @Override
    public List<ProgresoLeccion> buscarPorUsuarioIdYLeccionId(Long usuarioId, Long leccionId) {
        return (List<ProgresoLeccion>) this.sessionFactory.getCurrentSession().createCriteria(ProgresoLeccion.class)
                .add(Restrictions.eq("usuario.id", usuarioId))
                .add(Restrictions.eq("leccion.id", leccionId))
                .list();
    }

    @Override
    public void actualizar(ProgresoLeccion progresoLeccion) {
        this.sessionFactory.getCurrentSession().update(progresoLeccion);
    }

    @Override
    public List<ProgresoLeccion> buscarProgresosPorUsuario(Long usuarioId) {
        return (List<ProgresoLeccion>) this.sessionFactory.getCurrentSession().createCriteria(ProgresoLeccion.class)
                .add(Restrictions.eq("usuario.id", usuarioId))
                .list();
    }

    @Override
    public List<Leccion> darmeTodasLasLecciones() {
        List<Leccion> lecciones = this.sessionFactory.getCurrentSession().createCriteria(Leccion.class).list();
        return lecciones;
    }
    @Override
    public boolean experienciaOtorgada(Long usuarioId, Long leccionId) {
        List<ProgresoLeccion> progresos = buscarPorUsuarioIdYLeccionId(usuarioId, leccionId);

        return progresos.stream().anyMatch(ProgresoLeccion::getExperienciaOtorgada);
    }



}
