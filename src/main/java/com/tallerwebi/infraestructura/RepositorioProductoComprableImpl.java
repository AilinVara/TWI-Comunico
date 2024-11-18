package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ProductoComprable;
import com.tallerwebi.dominio.RepositorioProductoComprable;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioProductoComprableImpl implements RepositorioProductoComprable {

        final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioProductoComprableImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
        public void crearProducto(ProductoComprable producto) {
            sessionFactory.getCurrentSession().save(producto);
        }

}