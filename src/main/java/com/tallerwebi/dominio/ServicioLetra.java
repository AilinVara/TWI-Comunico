package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLetra {
    Letra obtenerLetra (Long letraId);
    Letra buscarLetraPorId(Long letraId);


    Letra buscarPorNombre(String nombre);
}
