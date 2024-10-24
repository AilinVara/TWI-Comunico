package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioTraduccion;
import com.tallerwebi.dominio.RepositorioEjercicioTraduccion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositorioEjercicioTraduccionImpl implements RepositorioEjercicioTraduccion {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEjercicioTraduccionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(EjercicioTraduccion ejercicioTraduccion) {
        this.sessionFactory.getCurrentSession().save(ejercicioTraduccion);
    }

    @Override
    @Transactional
    public EjercicioTraduccion buscarEjercicio(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (EjercicioTraduccion) session.createCriteria(EjercicioTraduccion.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }
}
