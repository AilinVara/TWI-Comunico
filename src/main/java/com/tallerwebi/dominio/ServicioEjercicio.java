package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEjercicio {

    void guardarEjercicio(Ejercicio ejercicio);

    Ejercicio obtenerEjercicio(Long ejercicioId);

    Boolean resolverEjercicioTraduccion(EjercicioTraduccion ejercicioTraduccion, Long opcionId);

    Boolean resolverEjercicioTraduccionSenia(EjercicioTraduccionSenia ejercicioTraduccion, Long opcionId);

    Boolean resolverEjercicioMatriz(String puntosSeleccionados, String puntosDeLaMatriz);

    List<String> convertirLetrasALista(String letras);

    Boolean resolverEjercicioFormaPalabras(String letras, String listaLetras);
}
