package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.Set;

@Entity
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
