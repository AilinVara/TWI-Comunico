package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCurso {

    List<Curso> obtenerCursosDisponibles();

    List<Curso> filtrarCursos(String tipo, String nivel);

    List<Curso> ordenarCursosPorFecha(String ordenFecha, List<Curso> cursosFiltrados);

    List<Curso> buscarCursosPorNombre(String nombre);

    void agregarCurso(Curso nuevoCurso);

}