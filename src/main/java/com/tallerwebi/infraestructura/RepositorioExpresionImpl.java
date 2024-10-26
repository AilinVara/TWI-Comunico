package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ExpresionSenias;
import com.tallerwebi.dominio.RepositorioExpresion;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import java.util.List;

@Repository
public class RepositorioExpresionImpl implements RepositorioExpresion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioExpresionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ExpresionSenias expresion) {
          this.sessionFactory.getCurrentSession().save(expresion);
    }

    @Override
    public ExpresionSenias obtenerUnaExpresionPorNombre(String nombre) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (ExpresionSenias) session.createCriteria(ExpresionSenias.class)
                .add(Restrictions.eq("nombre", nombre))
                .uniqueResult();
    }

    @Override
    public List<ExpresionSenias> obtenerExpresiones() {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<ExpresionSenias>) session.createCriteria(ExpresionSenias.class).list();
    }
}
