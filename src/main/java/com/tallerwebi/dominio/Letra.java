package com.tallerwebi.dominio;

import javax.persistence.*;


@Entity
public class Letra  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Lob
    @Column(name = "imagenSenias")
    private byte[] imagenSenias;
    @Lob
    @Column(name = "imagenBraille")
    private byte[] imagenBraille;

    public Letra() {
    }

    public Letra(String nombre, byte[] imagenSenias, byte[] imagenBraille) {
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

    public byte[] getImagenSenias() {
        return imagenSenias;
    }
    public byte[] getImagenBraille() {
        return imagenBraille;
    }

    public void setImagenBraille(byte[] imagenBraille) {
        this.imagenBraille = imagenBraille;
    }
    public void setImagenSenia(byte[] imagenSenias) {
        this.imagenSenias = imagenSenias;
    }
}
