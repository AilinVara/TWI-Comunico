package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioSuscripcion;
import com.tallerwebi.dominio.Suscripcion;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class RepositorioSuscripcionImpl implements RepositorioSuscripcion {
        final private SessionFactory sessionFactory;

        @Autowired
        public RepositorioSuscripcionImpl(SessionFactory sessionFactory) {
            this.sessionFactory = sessionFactory;
        }


        @Override
        public void guardarSuscripcion(Suscripcion suscripcion) {
            sessionFactory.getCurrentSession().save(suscripcion);
        }

        @Override
        public Suscripcion buscarSuscripcion(Long id) {
            return sessionFactory.getCurrentSession().get(Suscripcion.class, id);

        }

        @Override
        public Usuario verificarSuscripcionDeUsuario(Usuario usuario) {
            if (usuario == null) {
                return null;
            }

            Session session = sessionFactory.getCurrentSession();
            return (Usuario) session.createCriteria(Usuario.class)
                    .add(Restrictions.eq("id", usuario.getId()))
                    .add(Restrictions.isNotNull("suscripcion"))
                    .uniqueResult();
        }
}
