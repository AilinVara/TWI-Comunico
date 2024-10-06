package com.tallerwebi.dominio;

import javax.persistence.*;

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

    public ProgresoLeccion(){}

    public ProgresoLeccion(Usuario usuario, Leccion leccion, Ejercicio ejercicio) {
        this.usuario = usuario;
        this.ejercicio = ejercicio;
        this.leccion = leccion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
