package com.tallerwebi.dominio;

public interface ServicioVida {

     Vida obtenerVida(Long id);

    Boolean perderUnaVida(Long usuarioId);

    Boolean regenerarUnaVida(Long vidaId);


}
