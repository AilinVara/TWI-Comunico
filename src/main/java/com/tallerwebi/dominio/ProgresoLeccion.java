package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ProgresoLeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "leccion_id")
    private Leccion leccion;

    @ManyToOne
    @JoinColumn(name = "ejercicio_id")
    private Ejercicio ejercicio;

    Boolean completo = false;

    Boolean experienciaOtorgada = false;

    public ProgresoLeccion(){}

    public ProgresoLeccion(Usuario usuario, Leccion leccion, Ejercicio ejercicio) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.leccion = leccion;
    }

    public Boolean getCompleto() {
        return completo;
    }

    public void setCompleto(Boolean completo) {
        this.completo = completo;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Leccion getLeccion() {
        return leccion;
    }

    public void setLeccion(Leccion leccion) {
        this.leccion = leccion;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Ejercicio ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Boolean getExperienciaOtorgada() {return experienciaOtorgada;}

    public void setExperienciaOtorgada(Boolean experienciaOtorgada) {this.experienciaOtorgada = experienciaOtorgada;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgresoLeccion that = (ProgresoLeccion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
