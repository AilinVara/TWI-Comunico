package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.RepositorioLetra;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioLetraImpl implements RepositorioLetra {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioLetraImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Letra letra) {
    this.sessionFactory.getCurrentSession().save(letra);
    }

    @Override
    public Letra buscarUnaLetra(String nombre) {
         final Session session = sessionFactory.getCurrentSession();
         return (Letra) session.createCriteria(Letra.class)
                 .add(Restrictions.eq("nombre", nombre))
                 .uniqueResult();
    }

    @Override
    public List<Letra> buscarLetras() {
        final Session session = sessionFactory.getCurrentSession();
        return (List<Letra>) session.createCriteria(Letra.class).list();
    }
}
