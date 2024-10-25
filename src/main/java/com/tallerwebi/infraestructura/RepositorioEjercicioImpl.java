package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.EjercicioTraduccion;
import com.tallerwebi.dominio.RepositorioEjercicio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositorioEjercicioImpl implements RepositorioEjercicio {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEjercicioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Ejercicio ejercicio) {
        this.sessionFactory.getCurrentSession().save(ejercicio);
    }

    @Override
    @Transactional
    public Ejercicio buscarEjercicio(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Ejercicio) session.createCriteria(Ejercicio.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
