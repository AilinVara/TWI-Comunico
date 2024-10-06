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

import java.util.Comparator;
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
        // Usar una lista para ordenar en memoria
        if (ordenFecha.equals("asc")) {
            cursosFiltrados.sort(Comparator.comparing(Curso::getFecha));
        } else {
            cursosFiltrados.sort(Comparator.comparing(Curso::getFecha).reversed());
        }
        return cursosFiltrados;
    }
}


