package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaHome extends VistaWeb {
    public VistaHome(Page page) {
        super(page);
        page.navigate("localhost:8080/inicio");
    }

    public String obtenerTextoInicio() {
        return this.obtenerTextoDelElemento("#inicio");
    }

    public String obtenerTextoSenias() {
        return this.obtenerTextoDelElemento("#senias");
    }

    public String obtenerTextoBraille() {
        return this.obtenerTextoDelElemento("#braille");
    }

    public String obtenerTextoSuscripcion() {
        return this.obtenerTextoDelElemento("#suscripcion");
    }
}
