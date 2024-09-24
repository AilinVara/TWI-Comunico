package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Letra  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String imagenDeManos;

    public Letra() {
    }

    public Letra(Long id, String nombre, String imagenDeManos) {
        this.id = id;
        this.nombre = nombre;
        this.imagenDeManos = imagenDeManos;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenDeManos() {
        return imagenDeManos;
    }

    public void setImagenDeManos(String imagenDeManos) {
        this.imagenDeManos = imagenDeManos;
    }
}
