package com.tallerwebi.punta_a_punta.vistas;

import com.microsoft.playwright.Page;

public class VistaInicio extends VistaWeb{

    public VistaInicio(Page page) {
        super(page);
        page.navigate("http://localhost:8080/");
    }

    public String obtenerTextoDelBotonLogIn(){ return this.obtenerTextoDelElemento(".btn.btn-login"); }

    public String obtenerTextoDelBotonRegistrarse(){
        return this.obtenerTextoDelElemento(".btn.btn-signup");
    }

    public void darClickEnIniciarSesion(){
        this.darClickEnElElemento(".btn.btn-login");
    }

    public void darClickEnRegistrarse(){
        this.darClickEnElElemento(".btn.btn-signup");
    }
}
