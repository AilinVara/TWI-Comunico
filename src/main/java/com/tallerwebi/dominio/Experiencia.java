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
    private Integer cantidadExperiencia;
    private Integer nivel;



    public Experiencia() {
        this.cantidadExperiencia = 0;
        this.nivel = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidadExperiencia() {
        return cantidadExperiencia;
    }

    public void setCantidadExperiencia(int cantidadExperiencia) {
        this.cantidadExperiencia = cantidadExperiencia;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
