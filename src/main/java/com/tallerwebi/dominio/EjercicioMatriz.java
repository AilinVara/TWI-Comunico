package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue("matriz")
public class EjercicioMatriz extends Ejercicio {
    private String puntos;



    private String letra;

    public String getPuntos() {return this.puntos;}

    public void setPuntos(String puntos) {this.puntos = puntos;}

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjercicioMatriz that = (EjercicioMatriz) o;
        return Objects.equals(puntos, that.puntos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(puntos);
    }
}
