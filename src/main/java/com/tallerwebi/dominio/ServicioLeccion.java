package com.tallerwebi.dominio;

public interface ServicioLeccion {
    void guardarLeccion(Leccion leccion);

    Leccion obtenerLeccion(Long id);
}
