package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EjercicioFormaPalabra;
import com.tallerwebi.dominio.ServicioEjercicioFormaPalabra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorEjercicioFormaPalabra {

    @Autowired
    private ServicioEjercicioFormaPalabra servicioEjercicioVelocidad;

    private List<EjercicioFormaPalabra> ejercicios = new ArrayList<>();

    public ControladorEjercicioFormaPalabra() {
        // Crear los ejercicios
        ejercicios.add(new EjercicioFormaPalabra("gato.png", "GATO", Arrays.asList("R", "A", "G", "P", "S", "O", "T")));
        ejercicios.add(new EjercicioFormaPalabra("gato.png","PERRO", Arrays.asList("A", "N", "R", "O", "P", "E", "D")));
        ejercicios.add(new EjercicioFormaPalabra("gato.png","AVIÓN", Arrays.asList("C", "N", "I", "Ó", "M", "A", "V")));
    }

    @GetMapping("/leccionFormaPalabra")
    public String mostrarLeccion(Model model) {
        List<EjercicioFormaPalabra> ejercicios = new ArrayList<>();

        ejercicios.add(new EjercicioFormaPalabra("gato.png", "GATO", Arrays.asList("R", "A", "G", "P", "S", "O", "T")));
        ejercicios.add(new EjercicioFormaPalabra("gato.png","PERRO", Arrays.asList("A", "N", "R", "O", "P", "E", "D")));
        ejercicios.add(new EjercicioFormaPalabra("gato.png","AVIÓN", Arrays.asList("C", "N", "I", "Ó", "M", "A", "V")));

        model.addAttribute("ejercicios", ejercicios);
        model.addAttribute("indiceEjercicio", 0);
        model.addAttribute("mensaje", "");

        return "ejercicios-forma-palabra";
    }

    @PostMapping("/verificarRespuesta")
    public String verificarRespuesta(
            @RequestParam("respuestaUsuario") String respuestaUsuario,
            @RequestParam("indiceEjercicio") int indiceEjercicio,
            Model model) {

        EjercicioFormaPalabra ejercicio = ejercicios.get(indiceEjercicio);
        String respuestaCorrecta = ejercicio.getRespuestaCorrecta();

        if (respuestaUsuario.equalsIgnoreCase(respuestaCorrecta)) {
            model.addAttribute("mensaje", "Respuesta correcta");

            if (indiceEjercicio < ejercicios.size() - 1) {
                model.addAttribute("indiceEjercicio", indiceEjercicio + 1);
            } else {
                model.addAttribute("mensaje", "¡Has completado todos los ejercicios!");
                model.addAttribute("indiceEjercicio", indiceEjercicio);
            }
        } else {
            model.addAttribute("mensaje", "Respuesta incorrecta");
            model.addAttribute("indiceEjercicio", indiceEjercicio);
        }

        model.addAttribute("ejercicio", ejercicio);
        return "ejercicios-forma-palabra";
    }
}