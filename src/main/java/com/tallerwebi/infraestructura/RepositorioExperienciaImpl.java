package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Experiencia;
import com.tallerwebi.dominio.RepositorioExperiencia;
import com.tallerwebi.dominio.Vida;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class RepositorioExperienciaImpl implements RepositorioExperiencia {

    private final SessionFactory sessionFactory;
    @Autowired
    public RepositorioExperienciaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarExperiencia(Experiencia experiencia) {
        this.sessionFactory.getCurrentSession().save(experiencia);
    }

    @Override
    @Transactional
    public Experiencia buscarExperienciaPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        return (Experiencia) session.createCriteria(Experiencia.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void actualizarExperiencia(Experiencia experiencia) {
        this.sessionFactory.getCurrentSession().update(experiencia);
    }
}
