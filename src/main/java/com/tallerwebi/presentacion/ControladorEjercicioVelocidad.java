package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EjercicioVelocidad;
import com.tallerwebi.dominio.ServicioEjercicioVelocidad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class ControladorEjercicioVelocidad {

    @Autowired
    private ServicioEjercicioVelocidad servicioEjercicioVelocidad;

    @GetMapping("/leccion")
    public String mostrarLeccion(@RequestParam(value = "indice", defaultValue = "0") int indice, Model model) {
        model.addAttribute("ejercicios", servicioEjercicioVelocidad);
        model.addAttribute("indiceEjercicio", indice);
        return "ejerciciosVelocidad";
    }

    @GetMapping("/ejerciciosVelocidad")
    public String mostrarLeccion(Model model) {
        List<EjercicioVelocidad> ejercicios = new ArrayList<>();
        // Crear los ejercicios
        ejercicios.add(new EjercicioVelocidad("GATO", Arrays.asList("G", "A", "T", "O", "X", "Y", "Z")));
        ejercicios.add(new EjercicioVelocidad("PERRO", Arrays.asList("P", "E", "R", "O", "A", "B", "C")));
        ejercicios.add(new EjercicioVelocidad("AVIÓN", Arrays.asList("A", "V", "I", "Ó", "N", "Q", "R")));

        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("indiceEjercicio", 0);
        return "ejerciciosVelocidad";
    }
}