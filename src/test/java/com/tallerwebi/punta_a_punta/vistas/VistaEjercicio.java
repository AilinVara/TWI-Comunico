package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaEjercicio extends VistaWeb{
    public VistaEjercicio(Page page) {
        super(page);
        page.navigate("http://localhost:8080/ejercicio/1?leccion=1");
    }

    public String obtenerTextoDelTitulo(){
        return this.obtenerTextoDelElemento("h3");
    }
}
