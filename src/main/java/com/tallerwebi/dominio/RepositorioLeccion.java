package com.tallerwebi.dominio;

public interface RepositorioLeccion {
    void guardar(Leccion leccion);

    Leccion buscarPorTitulo(String primerLeccion);
}
