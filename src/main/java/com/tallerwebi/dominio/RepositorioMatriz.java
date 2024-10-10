package com.tallerwebi.dominio;

public interface RepositorioMatriz {
    void guardar(Matriz matriz);
    Matriz buscarMatriz(Long id);
    Matriz buscarMatrizPorEjercicio(Long id);
}
