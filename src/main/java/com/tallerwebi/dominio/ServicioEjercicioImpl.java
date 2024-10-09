package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioEjercicio")
@Transactional
public class ServicioEjercicioImpl implements ServicioEjercicio {

    private RepositorioEjercicio repositorioEjercicio;
    @Autowired
    private ServicioVida servicioVida;
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioEjercicioImpl(RepositorioEjercicio repositorioEjercicio) {
        this.repositorioEjercicio = repositorioEjercicio;
    }

    @Override
    public void guardarEjercicio(Ejercicio ejercicio) {

        this.repositorioEjercicio.guardar(ejercicio);
    }

    @Override
    public Ejercicio obtenerEjercicio(Long ejercicioId) {
        return this.repositorioEjercicio.buscarEjercicio(ejercicioId);
    }

    @Override
    public Boolean resolverEjercicio(Ejercicio ejercicio, Long opcionId) {
        return ejercicio.getOpcionCorrecta().getId().equals(opcionId);
    }

    @Override
    public Boolean perderVida(Long id) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(id);
        if (usuario != null) {
            return servicioVida.perderUnaVida(usuario);

        } else {
            return false;
        }

    }

}

