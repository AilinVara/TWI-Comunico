package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioSolicitudAmistad {

        void aceptarSolicitud(Long idSolicitud);
        void rechazarSolicitud(Long idSolicitud);
        Integer cantidadDeSolicitudes(Usuario usuario);

    void enviarSolicitud(Usuario solicitante, Usuario solicitado);
    List<SolicitudAmistad> buscarSolicitudes(Usuario usuario);
    Boolean comprobarSolicitudPendiente(Usuario solicitante, Usuario solicitado);

}