package com.tallerwebi.dominio;

public interface ServicioExperiencia {

    Experiencia obtenerExperiencia(Long usuarioId);

    Boolean ganarUnNivel(Long usuarioId);

    void ganar100DeExperiencia(Long usuarioId);

    void ganar300DeExperiencia(Long usuarioId);



}
