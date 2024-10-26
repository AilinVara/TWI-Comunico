package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioExpresion {
    public void guardar(ExpresionSenias expresion);

    public ExpresionSenias obtenerUnaExpresionPorNombre(String nombre);

    List<ExpresionSenias> obtenerExpresiones();
}
