package com.tallerwebi.dominio;

import java.io.IOException;
import java.util.List;

public interface ServicioLetra {

    Letra buscarPorNombre(String nombre);

    List<Letra> buscarTodasLasLetras();

    void crearTodasLasLetras() throws IOException;
}
