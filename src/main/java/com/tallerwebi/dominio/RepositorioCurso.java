package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCurso {

    List<Curso> obtenerTodosLosCursos();

    List<Curso> filtrarCursos(String tipo, String nivel);

    List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados);
}