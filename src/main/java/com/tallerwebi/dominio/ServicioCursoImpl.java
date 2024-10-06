package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioCurso")
@Transactional
public class ServicioCursoImpl implements ServicioCurso {

    @Autowired
    private RepositorioCurso repositorioCurso;

    @Override
    public List<Curso> obtenerCursosDisponibles() {
        return repositorioCurso.obtenerTodosLosCursos();
    }

    @Override
    public List<Curso> filtrarCursos(String tipo, String nivel) {
        return repositorioCurso.filtrarCursos(tipo, nivel);
    }

    @Override
    public List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados) {
        return repositorioCurso.ordenarCursosPorFecha(ordenFecha, cursosFiltrados);
    }

}


