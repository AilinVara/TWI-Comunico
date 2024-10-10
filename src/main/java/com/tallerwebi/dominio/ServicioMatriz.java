package com.tallerwebi.dominio;

public interface ServicioMatriz {

    Matriz obtenerMatriz(Long matrizId);

    Boolean resolverMatriz(String puntosSeleccionados, String puntosDeLaMatriz);

    Matriz obtenerMatrizPorEjercicio(Long ejercicioId);
}
