package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioVida;
import com.tallerwebi.dominio.Vida;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("repositorioVida")
public class RepositorioVidaImpl implements RepositorioVida {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioVidaImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarUnaVida(Vida vida) {
        this.sessionFactory.getCurrentSession().save(vida);
    }

    @Override
    public Vida buscarUnaVidaPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Vida) session.createCriteria(Vida.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void actualizarVida(Vida vida) {
        this.sessionFactory.getCurrentSession().update(vida);
    }


}
