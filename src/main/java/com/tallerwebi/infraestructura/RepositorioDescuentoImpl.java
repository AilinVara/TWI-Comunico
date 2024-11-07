package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioDescuentoImpl implements RepositorioDescuento{

        final private SessionFactory sessionFactory;

        public RepositorioDescuentoImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        @Override
        public void guardarDescuento(Descuento cupon) {
            sessionFactory.getCurrentSession().save(cupon);
        }

        @Override
        public void modificarCodigoDescuento(CodigoDescuento codigoDescuento) {
            sessionFactory.getCurrentSession().update(codigoDescuento);
        }

        @Override
        public void guardarCodigoDescuento(CodigoDescuento codigoDescuento) {
            sessionFactory.getCurrentSession().save(codigoDescuento);
        }

        @Override
        public CodigoDescuento buscarCodigoDescuento(Long id) {
            return sessionFactory.getCurrentSession().get(CodigoDescuento.class, id);
        }

        @Override
        public Descuento buscarDescuentoPorId(Long id) {
            Session session = sessionFactory.getCurrentSession();
            String hql = "FROM Descuento WHERE id = :id";
            org.hibernate.query.Query<Descuento> query = session.createQuery(hql, Descuento.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        }

        @Override
        public void eliminarDescuento(Descuento descuento) {
            sessionFactory.getCurrentSession().delete(descuento);
        }

    }
