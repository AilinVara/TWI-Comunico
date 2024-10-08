package com.tallerwebi.dominio;

import javax.persistence.ElementCollection;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class EjercicioVelocidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String palabra;

    @ElementCollection
    private List<String> letras;

    public EjercicioVelocidad() {}

    public EjercicioVelocidad(String palabra, List<String> letras) {
        this.palabra = palabra;
        this.letras = letras;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public List<String> getLetras() {
        return letras;
    }

    public void setLetras(List<String> letras) {
        this.letras = letras;
    }
}