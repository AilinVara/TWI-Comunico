package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class EjercicioMatriz extends Ejercicio {
    private String puntos;

    public String getPuntos() {return puntos;}

    public void setPuntos(String puntos) {this.puntos = puntos;}
}
