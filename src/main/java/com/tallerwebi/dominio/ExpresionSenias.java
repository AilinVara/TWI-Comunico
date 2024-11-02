package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class ExpresionSenias {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @Lob
    @Column(name = "imagenExpresion")
    private byte[] imagenExpresion;

    public ExpresionSenias() {}

    public ExpresionSenias(String nombre, byte[] imagenExpresion) {
        this.nombre = nombre;
        this.imagenExpresion = imagenExpresion;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getImagenExpresion() {
        return imagenExpresion;
    }

    public void setImagenExpresion(byte[] imagenExpresion) {
        this.imagenExpresion = imagenExpresion;
    }

}
