package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioCondicionLogroImpl  implements RepositorioCondicionLogro {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCondicionLogroImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(CondicionLogro condicionLogro) {
        this.sessionFactory.getCurrentSession().save(condicionLogro);
    }

    @Override
    public CondicionLogro buscarCondicion(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (CondicionLogro) session.createCriteria(CondicionLogro.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }


    @Override
    public void actualizar(CondicionLogro condicionLogro) {
        this.sessionFactory.getCurrentSession().update(condicionLogro);
    }

    @Override
    public List<CondicionLogro> buscarCondiciones() {
        final Session session = sessionFactory.getCurrentSession();
        return (List<CondicionLogro>) session.createCriteria(CondicionLogro.class).list();
    }
}

