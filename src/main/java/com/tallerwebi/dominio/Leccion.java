package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Leccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "leccion_id")
    private List<Ejercicio> ejercicios;

    private String tipo;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTipo(String titulo) {
        this.tipo = titulo;
    }

    public String getTipo() {
        return this.tipo;
    }

    public List<Ejercicio> getEjercicios() {
        return this.ejercicios;
    }

    public void setEjercicios(List<Ejercicio> ejercicios) {
        this.ejercicios = ejercicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leccion leccion = (Leccion) o;
        return Objects.equals(id, leccion.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
