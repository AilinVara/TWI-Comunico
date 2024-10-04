package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private LocalTime hora;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    private int capacidad;
    private int inscriptos;

    public enum Tipo {
        SEÑAS, BRAILLE
    }

    public enum Nivel {
        BÁSICO, INTERMEDIO, AVANZADO
    }
}
