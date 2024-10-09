package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class EjercicioFormaPalabra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imagen;
    private String respuestaCorrecta;
    private String letras;

    public EjercicioFormaPalabra() {}

    public EjercicioFormaPalabra(String imagen, String respuestaCorrecta, String letras) {
        this.imagen = imagen;
        this.respuestaCorrecta = respuestaCorrecta;
        this.letras = letras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLetras() {
        return letras;
    }

    public void setLetras(String letras) {
        this.letras = letras;
    }
}