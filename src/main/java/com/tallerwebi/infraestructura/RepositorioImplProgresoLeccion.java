package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ProgresoLeccion;
import com.tallerwebi.dominio.RepositorioProgresoLeccion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioImplProgresoLeccion implements RepositorioProgresoLeccion {


    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioImplProgresoLeccion(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ProgresoLeccion progresoLeccion) {
        this.sessionFactory.getCurrentSession().save(progresoLeccion);
    }
}
