package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
    public void agregarCurso(Curso curso) {
        repositorioCurso.agregarCurso(curso);
    }

    @Override
    public List<Curso> filtrarCursos(String tipo, String nivel) {
        return repositorioCurso.filtrarCursos(tipo, nivel);
    }

    @Override
    public List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados) {
        return repositorioCurso.ordenarCursosPorFecha(ordenFecha, cursosFiltrados);
    }

    @Override
    public List<Curso> buscarCursosPorNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            return obtenerCursosDisponibles();
        }
        List<Curso> cursos = repositorioCurso.obtenerTodosLosCursos();
        return cursos.stream()
                .filter(curso -> curso.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }

}


