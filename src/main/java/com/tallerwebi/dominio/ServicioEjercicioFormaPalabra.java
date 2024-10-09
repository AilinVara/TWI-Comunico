package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface ServicioEjercicioFormaPalabra {

    List<String> convertirLetrasALista(String letras);

    EjercicioFormaPalabra obtenerEjercicio (Long id);

    Boolean resolverEjercicio(String letras, String listaLetras);

}