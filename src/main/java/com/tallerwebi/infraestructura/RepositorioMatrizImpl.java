package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioMatriz;
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
    public void guardar(EjercicioMatriz ejercicioMatriz) {
        this.sessionFactory.getCurrentSession().save(ejercicioMatriz);
    }

    @Override
    @Transactional
    public EjercicioMatriz buscarMatriz(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (EjercicioMatriz) session.createCriteria(EjercicioMatriz.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
