package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.RepositorioLeccion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioLeccionImpl implements RepositorioLeccion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioLeccionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Leccion leccion) {
        this.sessionFactory.getCurrentSession().save(leccion);
    }

    @Override
    public List<Leccion> buscarPorTipo(String tipo) {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Leccion>) session.createCriteria(Leccion.class)
                .add(Restrictions.eq("tipo", tipo))
                .list();
    }

    public Leccion buscarPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Leccion) session.createQuery(
                        "SELECT l FROM Leccion l WHERE l.id = :id", Leccion.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}
