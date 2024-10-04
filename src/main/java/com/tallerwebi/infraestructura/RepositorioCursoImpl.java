package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Curso;
import com.tallerwebi.dominio.RepositorioCurso;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.util.List;

@Repository
public class RepositorioCursoImpl implements RepositorioCurso {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Curso> obtenerTodosLosCursos() {
        Session session = sessionFactory.getCurrentSession();

        Query<Curso> query = session.createQuery("from Curso", Curso.class);
        return query.getResultList();
    }
}

