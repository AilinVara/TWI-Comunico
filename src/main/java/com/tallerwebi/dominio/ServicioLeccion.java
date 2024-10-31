package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLeccion {
    void guardarLeccion(Leccion leccion);

    Leccion obtenerLeccion(Long id);

    List<Leccion> obtenerLeccionesPorTipo(String tipo);
}
