package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@DiscriminatorValue("traduccion")
public class EjercicioTraduccion extends Ejercicio{
    private String consigna;
    @OneToOne
    private Opcion opcionCorrecta;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Opcion> opcionesIncorrectas;

    public EjercicioTraduccion(){}

    public void setConsigna(String nombre) {
        this.consigna = nombre;
    }

    public Opcion getOpcionCorrecta() {
        return opcionCorrecta;
    }

    public void setOpcionCorrecta(Opcion opcion) {
        this.opcionCorrecta = opcion;
    }

    public String getConsigna() {
        return consigna;
    }

    public Set<Opcion> getOpcionesIncorrectas() {
        return opcionesIncorrectas;
    }

    public void setOpcionesIncorrectas(Set<Opcion> opcionesIncorrectas) {
        this.opcionesIncorrectas = opcionesIncorrectas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjercicioTraduccion ejercicioTraduccion = (EjercicioTraduccion) o;
        return Objects.equals(getId(), ejercicioTraduccion.getId()) && Objects.equals(consigna, ejercicioTraduccion.consigna) && Objects.equals(opcionCorrecta, ejercicioTraduccion.opcionCorrecta) && Objects.equals(opcionesIncorrectas, ejercicioTraduccion.opcionesIncorrectas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), consigna, opcionCorrecta, opcionesIncorrectas);
    }
}
