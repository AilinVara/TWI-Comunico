package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Curso;
import com.tallerwebi.dominio.ServicioCurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ControladorCurso {

    @Autowired
    private ServicioCurso servicioCurso;

    @GetMapping("/cursos")
    public String listarCursos(Model model) {
        List<Curso> cursos = servicioCurso.obtenerCursosDisponibles();
        model.addAttribute("cursos", cursos);
        return "cursos";
    }
}

