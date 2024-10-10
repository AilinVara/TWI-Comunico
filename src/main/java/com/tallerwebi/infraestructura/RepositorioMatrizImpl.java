package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Matriz;
import com.tallerwebi.dominio.RepositorioMatriz;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositorioMatrizImpl implements RepositorioMatriz {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioMatrizImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Matriz matriz) {
        this.sessionFactory.getCurrentSession().save(matriz);
    }

    @Override
    @Transactional
    public Matriz buscarMatriz(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Matriz) session.createCriteria(Matriz.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public Matriz buscarMatrizPorEjercicio(Long ejercicioId) {
        final Session session = sessionFactory.getCurrentSession();
        return (Matriz) session.createCriteria(Matriz.class)
                .createAlias("ejercicio", "e")
                .add(Restrictions.eq("e.id", ejercicioId))
                .uniqueResult();
    }

}
