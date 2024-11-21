package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaMapaLecciones extends VistaWeb{
    public VistaMapaLecciones(Page page) {
        super(page);
        page.navigate("http://localhost:8080/braille/lecciones/traduccion");
    }

    public String obtenerTextoDeLaPrimerLeccion(){
        return this.obtenerValorDelInput("#leccion-1");
    }

    public void darClickEnLaLeccion(int numero){
        this.darClickEnElElemento("#leccion-" + numero);
    }
}
