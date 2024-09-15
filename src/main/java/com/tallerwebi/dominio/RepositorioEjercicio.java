package com.tallerwebi.dominio;

public interface RepositorioEjercicio {

    public void guardar(Ejercicio ejercicio);

    public Ejercicio buscarUno(Long id);
}
