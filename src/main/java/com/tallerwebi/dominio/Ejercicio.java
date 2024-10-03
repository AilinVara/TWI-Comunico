package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String consigna;

    @OneToOne
    private Opcion opcionCorrecta;
    @ManyToOne
    private Leccion leccion;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Opcion> opcionesIncorrectas;

    public Ejercicio(){}



    public void setConsigna(String nombre) {
        this.consigna = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Opcion getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(Opcion opcion) {
        this.opcionCorrecta = opcion;
    }

    public String getConsigna() {
        return consigna;
    }

    public List<Opcion> getOpcionesIncorrectas() {
        return opcionesIncorrectas;
    }

    public void setOpcionesIncorrectas(List<Opcion> opcionesIncorrectas) {
        this.opcionesIncorrectas = opcionesIncorrectas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ejercicio ejercicio = (Ejercicio) o;
        return Objects.equals(id, ejercicio.id) && Objects.equals(consigna, ejercicio.consigna) && Objects.equals(opcionCorrecta, ejercicio.opcionCorrecta) && Objects.equals(opcionesIncorrectas, ejercicio.opcionesIncorrectas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, consigna, opcionCorrecta, opcionesIncorrectas);
    }
}
