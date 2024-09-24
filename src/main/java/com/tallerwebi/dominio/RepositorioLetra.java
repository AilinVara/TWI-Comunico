package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLetra {

    public void guardar(Letra letra);

    public Letra buscarUnaLetra(String nombre);

    List<Letra> buscarLetras();
}
