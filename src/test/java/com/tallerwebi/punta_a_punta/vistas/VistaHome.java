package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaHome extends VistaWeb{
    public VistaHome(Page page) {
        super(page);
        page.navigate("localhost:8080/inicio");
    }

    public String obtenerTextoDelTitulo(){
        return this.obtenerTextoDelElemento("#bienvenida");
    }

    public void darClickEnElBotonBraille(){
        this.darClickEnElElemento("#braille");
    }

}
