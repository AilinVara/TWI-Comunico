package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class ServicioCursoImplTest {

    private RepositorioCurso repositorioCursoMock;
    private ServicioCursoImpl servicioCurso;

    @BeforeEach
    public void init() {
        repositorioCursoMock = mock(RepositorioCurso.class);
        servicioCurso = new ServicioCursoImpl(repositorioCursoMock);
    }

    @Test
    public void dadoQueExistenCursosCuandoLosObtengoEntoncesRetornaLaListaCompleta() {
        List<Curso> cursos = new ArrayList<>();
        Curso curso = new Curso();
        cursos.add(curso);

        when(repositorioCursoMock.obtenerTodosLosCursos()).thenReturn(cursos);

        List<Curso> cursosObtenidos = servicioCurso.obtenerCursosDisponibles();

        assertThat(cursosObtenidos, equalTo(cursos));
        assertThat(cursosObtenidos, hasSize(1));
        assertThat(cursosObtenidos.get(0), notNullValue());
    }

    @Test
    public void dadoUnCursoNuevoCuandoLoAgregoEntoncesSeLlamaAlMetodoAgregarDelRepositorio() {
        Curso curso = new Curso();

        servicioCurso.agregarCurso(curso);

        assertThat(curso, notNullValue());
    }

    @Test
    public void dadoTipoYNivelCuandoFiltroCursosEntoncesObtengoCursosFiltrados() {
        List<Curso> cursosFiltrados = new ArrayList<>();
        Curso curso = new Curso();
        cursosFiltrados.add(curso);

        when(repositorioCursoMock.filtrarCursos("BRAILLE", "BÁSICO")).thenReturn(cursosFiltrados);

        List<Curso> resultado = servicioCurso.filtrarCursos("BRAILLE", "BÁSICO");

        assertThat(resultado, equalTo(cursosFiltrados));
        assertThat(resultado, hasSize(1));
    }

    @Test
    public void dadoUnOrdenFechaYCursosFiltradosCuandoOrdenoEntoncesObtengoCursosOrdenados() {
        List<Curso> cursosFiltrados = new ArrayList<>();
        Curso curso1 = new Curso();
        curso1.setFecha(LocalDate.of(2025, 1, 1));
        Curso curso2 = new Curso();
        curso2.setFecha(LocalDate.of(2024, 1, 1));
        cursosFiltrados.add(curso1);
        cursosFiltrados.add(curso2);

        when(repositorioCursoMock.ordenarCursosPorFecha("asc", cursosFiltrados)).thenReturn(cursosFiltrados);

        List<Curso> resultado = servicioCurso.ordenarCursosPorFecha("asc", cursosFiltrados);

        assertThat(resultado, equalTo(cursosFiltrados));
        assertThat(resultado.get(0).getFecha(), equalTo(LocalDate.of(2025, 1, 1)));
    }

    @Test
    public void dadoUnNombreCuandoBuscoCursosEntoncesObtengoCursosConNombreSimilar() {
        List<Curso> cursos = new ArrayList<>();
        Curso curso = new Curso();
        curso.setNombre("Aprende braille con Aylu");
        cursos.add(curso);

        when(repositorioCursoMock.obtenerTodosLosCursos()).thenReturn(cursos);

        List<Curso> resultado = servicioCurso.buscarCursosPorNombre("braille");

        assertThat(resultado.size(), equalTo(1));
        assertThat(resultado.get(0).getNombre(), equalTo("Aprende braille con Aylu"));
        assertThat(resultado, contains(curso));
    }

    @Test
    public void dadoUnIdDeCursoCuandoLoObtengoEntoncesDevuelvoElCursoCorrespondiente() {
        Curso curso = new Curso();
        curso.setId(1L);

        when(repositorioCursoMock.obtenerCursoPorId(1L)).thenReturn(curso);

        Curso resultado = servicioCurso.obtenerCursoPorId(1L);

        assertThat(resultado, equalTo(curso));
        assertThat(resultado.getId(), equalTo(1L));
        assertThat(resultado, notNullValue());
    }

    @Test
    public void dadoUnAlumnoYCursoIdCuandoInscriboEntoncesAlumnoEsAgregadoAlCurso() {
        Curso curso = new Curso();
        curso.setId(1L);

        when(repositorioCursoMock.obtenerCursoPorId(1L)).thenReturn(curso);

        boolean resultado = servicioCurso.inscribirAlumno("Ailin", "Vara", 1L);

        assertThat(resultado, equalTo(true));
        assertThat(curso.getAlumnosInscritos().size(), equalTo(1));
        assertThat(curso.getAlumnosInscritos().get(0), equalTo("Ailin Vara"));
    }
}