package com.tallerwebi.dominio;

public interface RepositorioVida {

    void guardarUnaVida(Vida vida);
    Vida buscarUnaVidaPorId(Long id);
    void actualizarVida(Vida vida);

}
