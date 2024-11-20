package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaMenuBraille extends VistaWeb{
    public VistaMenuBraille(Page page) {
        super(page);
        page.navigate("localhost:8080/braille");
    }

    public String obtenerTextoDeLaLeccionTraduccion(){
        return this.obtenerValorDelInput("#letras");
    }

    public void darClickEnElElementoTraduccion(){
        this.darClickEnElElemento("#traduccion-link");
    }
}
