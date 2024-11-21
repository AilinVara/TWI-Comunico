package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ServicioSolicitudAmistadImpl implements ServicioSolicitudAmistad{

        private RepositorioSolicitudAmistad repositorioSolicitudAmistad;

        @Autowired
        public ServicioSolicitudAmistadImpl (RepositorioSolicitudAmistad repositorioSolicitudAmistad){
            this.repositorioSolicitudAmistad = repositorioSolicitudAmistad;
        }

        @Override
        public void aceptarSolicitud(Long idSolicitud)  {

            if (idSolicitud != null) {

                SolicitudAmistad solicitud =  repositorioSolicitudAmistad.buscarSolicitudPorId(idSolicitud);

                Amigo amigo= new Amigo(solicitud.getSolicitado(), solicitud.getSolicitante());
                Amigo amigo2 = new Amigo(solicitud.getSolicitante(), solicitud.getSolicitado());

                repositorioSolicitudAmistad.crearAmistad(amigo);
                repositorioSolicitudAmistad.crearAmistad(amigo2);

                repositorioSolicitudAmistad.aceptarSolicitud(solicitud, amigo);
                repositorioSolicitudAmistad.eliminarSolicitud(solicitud);
            }

        }

        @Override
        public void rechazarSolicitud(Long idSolicitud) {

            if (idSolicitud != null) {

                SolicitudAmistad solicitud =  repositorioSolicitudAmistad.buscarSolicitudPorId(idSolicitud);

                repositorioSolicitudAmistad.eliminarSolicitud(solicitud);
            }
        }

        @Override
        public Integer cantidadDeSolicitudes(Usuario usuario) {
            return repositorioSolicitudAmistad.buscarSolicitudes(usuario).size();
        }

        @Override
        public void enviarSolicitud(Usuario solicitante, Usuario solicitado) {
            SolicitudAmistad solicitud = new SolicitudAmistad();
            solicitud.setSolicitante(solicitante);
            solicitud.setSolicitado(solicitado);
            solicitud.setFechaSolicitud(LocalDateTime.now());
            repositorioSolicitudAmistad.guardar(solicitud);
        }

        @Override
        public List<SolicitudAmistad> buscarSolicitudes(Usuario usuario) {
            return repositorioSolicitudAmistad.buscarSolicitudes(usuario);
        }

        @Override
        public Boolean comprobarSolicitudPendiente(Usuario solicitante, Usuario solicitado) {
            SolicitudAmistad solicitud =  repositorioSolicitudAmistad.buscarSolicitudAmistadEntreDosPersonas(solicitante, solicitado);
            if(solicitud!=null){
                return true;
            }
            return false;
        }
}