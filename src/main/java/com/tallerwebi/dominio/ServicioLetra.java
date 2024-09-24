package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLetra {

    Letra buscarPorNombre(String nombre);

    List<Letra> buscarTodasLasLetras();
}
