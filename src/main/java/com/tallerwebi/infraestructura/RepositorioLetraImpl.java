package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Letra;
import com.tallerwebi.dominio.RepositorioLetra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public Letra buscarUnaLetra(Long id) {
        return null;
    }
}
