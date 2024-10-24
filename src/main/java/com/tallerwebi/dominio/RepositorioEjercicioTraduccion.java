package com.tallerwebi.dominio;

public interface RepositorioEjercicioTraduccion {

    public void guardar(EjercicioTraduccion ejercicioTraduccion);

    public EjercicioTraduccion buscarEjercicio(Long id);
}
