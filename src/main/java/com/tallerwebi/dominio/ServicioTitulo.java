package com.tallerwebi.dominio;

public interface ServicioTitulo {

    String obtenerTitulo(Long usuarioId);

    void actualizarTituloSegunExperiencia(Long usuarioId);

    void obtenerComunicoPointsCuandoConsigueTitulo(Long usuarioId);

    int obtenerTiempoRegeneracionPorTitulo(Long usuarioId);

    int obtenerExperienciaMaximaPorTitulo(String titulo);
}
