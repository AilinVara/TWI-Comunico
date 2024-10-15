package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioLogroImpl implements RepositorioLogro {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioLogroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Logro logro) {
        this.sessionFactory.getCurrentSession().save(logro);

    }

    @Override
    public Logro buscarLogro(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Logro) session.createCriteria(Logro.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }


    @Override
    public Logro buscarLogroPorCondicion(Long condicionId) {
        final Session session = sessionFactory.getCurrentSession();
        return (Logro) session.createCriteria(Logro.class)
                .createAlias("condicion", "c")
                .add(Restrictions.eq("c.id", condicionId))
                .uniqueResult();
    }

    @Override
    public void actualizar(Logro logro) {
        this.sessionFactory.getCurrentSession().update(logro);
    }

    @Override
    public List<Logro> obtenerTodosLosLogros() {
        Session session = sessionFactory.getCurrentSession();
        Query<Logro> query = session.createQuery("from Logro", Logro.class);
        return query.getResultList();
    }


}

