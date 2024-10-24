package com.tallerwebi.dominio;

public interface RepositorioMatriz {
    void guardar(EjercicioMatriz ejercicioMatriz);
    EjercicioMatriz buscarMatriz(Long id);
}
