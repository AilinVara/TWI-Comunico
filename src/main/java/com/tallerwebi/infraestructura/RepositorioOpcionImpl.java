package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Opcion;
import com.tallerwebi.dominio.RepositorioOpcion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioOpcionImpl implements RepositorioOpcion {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioOpcionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Opcion opcion) {
        this.sessionFactory.getCurrentSession().save(opcion);
    }
}
