package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLeccion {
    void guardar(Leccion leccion);

    List<Leccion> buscarPorTipo(String tipo);

    Leccion buscarPorId(Long id);
}
