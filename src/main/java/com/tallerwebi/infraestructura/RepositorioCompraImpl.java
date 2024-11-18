package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Compra;
import com.tallerwebi.dominio.RepositorioCompra;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioCompraImpl implements RepositorioCompra{
        final private SessionFactory sessionFactory;

        @Autowired
        public RepositorioCompraImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void crearCompra(Compra compra) {
            sessionFactory.getCurrentSession().save(compra);
        }
    }