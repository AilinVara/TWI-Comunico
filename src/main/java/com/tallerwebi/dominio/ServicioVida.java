package com.tallerwebi.dominio;

public interface ServicioVida {

     Vida obtenerVida(Long id);

    Boolean perderUnaVida(Usuario usuario);

    Boolean regenerarUnaVida(Long id);


}
