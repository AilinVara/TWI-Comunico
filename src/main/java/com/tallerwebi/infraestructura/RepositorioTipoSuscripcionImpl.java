package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioTipoSuscripcion;
import com.tallerwebi.dominio.TipoSuscripcion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class RepositorioTipoSuscripcionImpl implements RepositorioTipoSuscripcion {

        private final SessionFactory sessionFactory;

        public RepositorioTipoSuscripcionImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }

        @Override
        public String obtenerDescripcionSuscripciones(Long id) {
            Session session = sessionFactory.getCurrentSession();
            TipoSuscripcion tipoSuscripcion = session.get(TipoSuscripcion.class, id);
            return tipoSuscripcion != null ? tipoSuscripcion.getDescripcion() : null;
        }
}
