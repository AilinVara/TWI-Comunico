package com.tallerwebi.dominio;

public interface ServicioUsuario {
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
}
