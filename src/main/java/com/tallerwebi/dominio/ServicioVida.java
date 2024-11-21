package com.tallerwebi.dominio;

public interface ServicioVida {

     Vida obtenerVida(Long usuarioId);

    Boolean perderUnaVida(Long usuarioId);

    void regenerarVidasDeTodosLosUsuarios();

    void actualizarVida(Vida vida);
}
