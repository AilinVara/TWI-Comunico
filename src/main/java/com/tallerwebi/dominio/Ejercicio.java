package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Ejercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String tipoEjercicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipoEjercicio() {
        return tipoEjercicio;
    }

    public void setTipoEjercicio(String tipoEjercicio) {
        this.tipoEjercicio = tipoEjercicio;
    }

}