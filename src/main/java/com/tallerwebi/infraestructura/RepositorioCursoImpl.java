package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Curso;
import com.tallerwebi.dominio.RepositorioCurso;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Repository
public class RepositorioCursoImpl implements RepositorioCurso {

    @Autowired
    private SessionFactory sessionFactory;

    public RepositorioCursoImpl(SessionFactory sessionFactory) {
    }

    @Override
    public List<Curso> obtenerTodosLosCursos() {
        Session session = sessionFactory.getCurrentSession();
        Query<Curso> query = session.createQuery("from Curso", Curso.class);
        return query.getResultList();
    }

    @Override
    public void agregarCurso(Curso curso) {
        Session session = sessionFactory.getCurrentSession();
        session.save(curso);
    }

    @Override
    public void actualizarCurso(Curso curso) {
        Session session = sessionFactory.getCurrentSession();
        session.update(curso);
    }


    @Override
    public List<Curso> filtrarCursos(String tipo, String nivel) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Curso.class);

        if (tipo != null && !tipo.isEmpty()) {
            criteria.add(Restrictions.eq("tipo", tipo));
        }

        if (nivel != null && !nivel.isEmpty()) {
            criteria.add(Restrictions.eq("nivel", nivel));
        }

        return criteria.list();
    }

    @Override
    public List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados) {
        if (ordenFecha.equals("asc")) {
            cursosFiltrados.sort(Comparator.comparing(Curso::getFecha));
        } else {
            cursosFiltrados.sort(Comparator.comparing(Curso::getFecha).reversed());
        }
        return cursosFiltrados;
    }

    @Override
    public List<Curso> obtenerCursoPorFecha(LocalDate fecha) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Curso.class);
        criteria.add(Restrictions.eq("fecha", fecha));
        return criteria.list();
    }

    @Override
    public Curso obtenerCursoPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Curso.class, id);
    }
}