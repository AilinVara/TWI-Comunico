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
    private String imagenSenias;
    private String imagenBraille;

    public Letra() {
    }

    public Letra(String nombre, String imagenSenias, String imagenBraille) {
        this.nombre = nombre;
        this.imagenSenias = imagenSenias;
        this.imagenBraille = imagenBraille;
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

    public String getImagenSenias() {
        return imagenSenias;
    }
    public String getImagenBraille() {
        return imagenBraille;
    }

    public void setImagenSenias(String imagenSenias) {
        this.imagenSenias = imagenSenias;
    }

}
