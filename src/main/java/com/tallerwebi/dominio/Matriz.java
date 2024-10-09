package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Matriz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String puntos;

    @OneToOne
    private Ejercicio ejercicio;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getPuntos() {return puntos;}

    public void setPuntos(String puntos) {this.puntos = puntos;}

    public Ejercicio getEjercicio() {return ejercicio;}

    public void setEjercicio(Ejercicio ejercicio) {this.ejercicio = ejercicio;}
}
