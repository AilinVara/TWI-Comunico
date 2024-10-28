package com.tallerwebi.dominio;

public interface RepositorioExperiencia {

    void guardarExperiencia(Experiencia experiencia);
    Experiencia buscarExperienciaPorId(Long id);
    void actualizarExperiencia(Experiencia experiencia);
}
