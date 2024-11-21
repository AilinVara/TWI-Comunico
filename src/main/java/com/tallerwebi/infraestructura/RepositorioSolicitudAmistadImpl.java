package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Amigo;
import com.tallerwebi.dominio.RepositorioSolicitudAmistad;
import com.tallerwebi.dominio.SolicitudAmistad;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class RepositorioSolicitudAmistadImpl implements RepositorioSolicitudAmistad {

    final private SessionFactory sessionFactory;

    @Autowired
    public RepositorioSolicitudAmistadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(SolicitudAmistad solicitud) {
        sessionFactory.getCurrentSession().save(solicitud);
    }

    @Override
    public List<SolicitudAmistad> buscarSolicitudes(Usuario usuario) {
        return sessionFactory.getCurrentSession().createQuery("from SolicitudAmistad s where s.solicitado = :usuario", SolicitudAmistad.class)
                .setParameter("usuario", usuario)
                .getResultList();
    }

    @Override
    public SolicitudAmistad buscarSolicitudAmistadEntreDosPersonas(Usuario aceptante, Usuario solicitante) {
        return sessionFactory.getCurrentSession().createQuery("from SolicitudAmistad s where s.solicitado = :aceptante and s.solicitante = :solicitante", SolicitudAmistad.class)
                .setParameter("aceptante", aceptante)
                .setParameter("solicitante", solicitante)
                .uniqueResult();
    }

    @Override
    public void aceptarSolicitud(SolicitudAmistad solicitud, Amigo amigo) {
        solicitud.setAceptada(true);
        sessionFactory.getCurrentSession().update(solicitud);
    }


    @Override
    public void eliminarSolicitud(SolicitudAmistad solicitud) {
        sessionFactory.getCurrentSession().delete(solicitud);
    }

    @Override
    public SolicitudAmistad buscarSolicitudPorId(Long idSolicitud) {
        return sessionFactory.getCurrentSession().createQuery("from SolicitudAmistad s where s.id = :idSolicitud", SolicitudAmistad.class)
                .setParameter("idSolicitud", idSolicitud)
                .uniqueResult();
    }

    @Override
    public void crearAmistad(Amigo amigo) {
        sessionFactory.getCurrentSession().save(amigo);
    }

}
