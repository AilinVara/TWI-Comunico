package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.RepositorioEjercicio;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
