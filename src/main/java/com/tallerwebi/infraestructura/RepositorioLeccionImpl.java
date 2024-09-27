package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Leccion;
import com.tallerwebi.dominio.RepositorioLeccion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioLeccionImpl implements RepositorioLeccion {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioLeccionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Leccion leccion) {
        this.sessionFactory.getCurrentSession().save(leccion);
    }
}
