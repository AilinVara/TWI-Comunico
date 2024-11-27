package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("senia")
public class EjercicioTraduccionSenia extends Ejercicio{
    private String consigna;
    @OneToOne
    private Opcion opcionCorrecta;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Opcion> opcionesIncorrectas;

    public EjercicioTraduccionSenia(){}

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
}
