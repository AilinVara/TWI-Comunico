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

    @ElementCollection
    private List<String> letras;

    public EjercicioFormaPalabra() {}

    public EjercicioFormaPalabra(String imagen, String respuestaCorrecta, List<String> letras) {
        this.imagen = imagen;
        this.respuestaCorrecta = respuestaCorrecta;
        this.letras = letras;
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

    public List<String> getLetras() {
        return letras;
    }

    public void setLetras(List<String> letras) {
        this.letras = letras;
    }
}