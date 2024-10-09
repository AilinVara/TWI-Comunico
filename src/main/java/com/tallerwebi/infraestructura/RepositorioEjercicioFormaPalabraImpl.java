package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioFormaPalabra;
import com.tallerwebi.dominio.RepositorioEjercicioFormaPalabra;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioEjercicioFormaPalabraImpl implements RepositorioEjercicioFormaPalabra {

    private final SessionFactory sessionFactory;

    public RepositorioEjercicioFormaPalabraImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(EjercicioFormaPalabra ejercicioFormaPalabra) {
        this.sessionFactory.getCurrentSession().save(ejercicioFormaPalabra);
    }

    @Override
    public EjercicioFormaPalabra buscar(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (EjercicioFormaPalabra)session.createCriteria(EjercicioFormaPalabra.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
    }
}
