package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class CondicionLogro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;  // Ej "Completar sin perder vidas"
    private boolean completada;

    @ManyToOne
    @JoinColumn(name = "logro_id")
    private Logro logro;

    public CondicionLogro() {}

    public CondicionLogro(String descripcion) {
        this.descripcion = descripcion;
        this.completada = false;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public Logro getLogro() {
        return logro;
    }

    public void setLogro(Logro logro) {
        this.logro = logro;
    }
}
