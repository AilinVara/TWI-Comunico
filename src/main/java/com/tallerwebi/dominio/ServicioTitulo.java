package com.tallerwebi.dominio;

public interface ServicioTitulo {

    String obtenerTitulo(Long usuarioId);

    void actualizarTituloSegunExperiencia(Long usuarioId);

    void obtenerVidasYComunicoPointsCuandoConsigueTitulo(Long usuarioId);
}
