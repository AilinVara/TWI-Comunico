package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Experiencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cantidadExperiencia;
    private int nivel;



    public Experiencia() {
        this.cantidadExperiencia = 0;
        this.nivel = 0;
    }

    public int getCantidadExperiencia() {
        return cantidadExperiencia;
    }

    public void setCantidadExperiencia(int cantidadExperiencia) {
        this.cantidadExperiencia = cantidadExperiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
