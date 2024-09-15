package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String consigna;


    @OneToOne
    private Opcion opcionCorrecta;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Opcion> opcionesIncorrectas;

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

    public void setOpcionCorrecta(Opcion opcionCorrecta) {
        this.opcionCorrecta = opcionCorrecta;
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
}
