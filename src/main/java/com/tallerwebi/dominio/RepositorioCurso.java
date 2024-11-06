package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioCurso {

    List<Curso> obtenerTodosLosCursos();

    List<Curso> obtenerCursoPorFecha(LocalDate fecha);

    List<Curso> filtrarCursos(String tipo, String nivel);

    List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados);

    Curso obtenerCursoPorId(Long id);

    void agregarCurso(Curso curso);

    void actualizarCurso(Curso curso);
}