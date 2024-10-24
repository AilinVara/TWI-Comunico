package com.tallerwebi.dominio;

public interface ServicioEjercicio {

    void guardarEjercicio(EjercicioTraduccion ejercicioTraduccion);

    Ejercicio obtenerEjercicio(Long ejercicioId);

    Boolean resolverEjercicioTraduccion(EjercicioTraduccion ejercicioTraduccion, Long opcionId);

    Boolean resolverEjercicioMatriz(String puntosSeleccionados, String puntosDeLaMatriz);

}
