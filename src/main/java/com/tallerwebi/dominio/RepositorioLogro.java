package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioLogro {

    void guardar(Logro logro);
    Logro buscarLogro(Long id);
    Logro buscarLogroPorCondicion(Long id);
    void actualizar(Logro logro);
    List<Logro> obtenerTodosLosLogros();


}
