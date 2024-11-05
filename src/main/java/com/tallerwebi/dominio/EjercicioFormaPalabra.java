package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
@DiscriminatorValue("forma-palabras")
public class EjercicioFormaPalabra extends Ejercicio{
    private String imagen;
    private String respuestaCorrecta;
    private String letras;

    public EjercicioFormaPalabra() {}

    public EjercicioFormaPalabra(String imagen, String respuestaCorrecta, String letras) {
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

    public String getLetras() {
        return letras;
    }

    public void setLetras(String letras) {
        this.letras = letras;
    }


}