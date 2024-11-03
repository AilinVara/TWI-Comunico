package com.tallerwebi.dominio;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioExperiencia")
@Transactional
public class ServicioExperienciaImpl implements ServicioExperiencia {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioExperiencia repositorioExperiencia;

    public ServicioExperienciaImpl(RepositorioUsuario repositorioUsuario, RepositorioExperiencia repositorioExperiencia) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioExperiencia = repositorioExperiencia;
    }

    @Override
    public Experiencia obtenerExperiencia(Long usuarioId) {

        return this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getExperiencia();
    }

    @Override
    public Boolean ganarUnNivel(Long usuarioId) {
        boolean desbloqueado = false;
        Experiencia experiencia = this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getExperiencia();
        int nivel = this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getExperiencia().getNivel();
        if (experiencia.getCantidadExperiencia() >= 2000)
            experiencia.setNivel(experiencia.getNivel() + 1);
        desbloqueado = true;
        return desbloqueado;
    }

    @Override
    public void ganar100DeExperiencia(Long usuarioId) {

        Experiencia experiencia = this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getExperiencia();

        if (experiencia.getCantidadExperiencia() >= 0 && experiencia.getCantidadExperiencia() < 5000) {
            experiencia.setCantidadExperiencia(experiencia.getCantidadExperiencia() + 100);

            repositorioExperiencia.actualizarExperiencia(experiencia);

        }

    }

    @Override
    public void ganar1000DeExperiencia(Long usuarioId) {
        Experiencia experiencia = this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getExperiencia();

        if (experiencia.getCantidadExperiencia() >= 0) {
            experiencia.setCantidadExperiencia(experiencia.getCantidadExperiencia() + 1000);

            repositorioExperiencia.actualizarExperiencia(experiencia);
        }
    }


}

