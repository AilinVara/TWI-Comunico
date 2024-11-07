package com.tallerwebi.dominio;

public interface RepositorioSuscripcion {
        void guardarSuscripcion(Suscripcion suscripcion);
        Suscripcion buscarSuscripcion(Long id);
        Usuario verificarSuscripcionDeUsuario(Usuario usuario);
}
