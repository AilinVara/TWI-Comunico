package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioSolicitudAmistad {

        void guardar(SolicitudAmistad solicitud);
        List<SolicitudAmistad> buscarSolicitudes(Usuario usuario);
        SolicitudAmistad buscarSolicitudAmistadEntreDosPersonas(Usuario aceptante, Usuario solicitante);
        void aceptarSolicitud(SolicitudAmistad solicitud, Amigo amigo);
        void eliminarSolicitud(SolicitudAmistad solicitud);
        SolicitudAmistad buscarSolicitudPorId(Long idSolicitud);
        void crearAmistad(Amigo amigo);
    }
