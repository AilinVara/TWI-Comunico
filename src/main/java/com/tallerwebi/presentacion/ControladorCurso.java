package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Curso;
import com.tallerwebi.dominio.ServicioCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class ControladorCurso {

    @Autowired
    private ServicioCurso servicioCurso;

    @RequestMapping(value = "/cursos", method = RequestMethod.GET)
    public String listarCursos(Model model) {
        List<Curso> cursos = servicioCurso.obtenerCursosDisponibles();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }

    @RequestMapping(value = "/crear-curso", method = RequestMethod.GET)
    public String mostrarFormularioCrearCurso(Model model) {
        return "crear-curso";
    }

    @RequestMapping(value = "/guardarCurso", method = RequestMethod.POST)
    public String guardarCurso(@RequestParam String nombre,
                               @RequestParam String descripcion,
                               @RequestParam String fecha,
                               @RequestParam String hora,
                               @RequestParam String tipo,
                               @RequestParam String nivel,
                               @RequestParam int capacidad) {

        Curso nuevoCurso = new Curso();
        nuevoCurso.setNombre(nombre);
        nuevoCurso.setDescripcion(descripcion);
        nuevoCurso.setFecha(LocalDate.parse(fecha));
        nuevoCurso.setHora(LocalTime.parse(hora));
        nuevoCurso.setTipo(tipo);
        nuevoCurso.setNivel(nivel);
        nuevoCurso.setCapacidad(capacidad);
        nuevoCurso.setInscriptos(0);

        servicioCurso.agregarCurso(nuevoCurso);

        return "redirect:/cursos";
    }

    @RequestMapping(value = "/filtrarCursos", method = RequestMethod.POST)
    public String filtrarYOrdenarCursos(@RequestParam(required = false) String tipo,
                                        @RequestParam(required = false) String nivel,
                                        @RequestParam(required = false) String ordenFecha,
                                        Model model) {

        List<Curso> cursosFiltrados = servicioCurso.filtrarCursos(tipo, nivel);

        if (ordenFecha != null && !ordenFecha.isEmpty()) {
            cursosFiltrados = servicioCurso.ordenarCursosPorFecha(ordenFecha, cursosFiltrados);
        }

        model.addAttribute("cursos", cursosFiltrados);
        return "cursos";
    }

    @RequestMapping(value = "/buscarCursos", method = RequestMethod.POST)
    public String buscarCursos(@RequestParam(required = false) String nombre,
                               Model model) {
        List<Curso> cursosBuscados = servicioCurso.buscarCursosPorNombre(nombre);
        model.addAttribute("cursos", cursosBuscados);
        return "cursos";
    }
}