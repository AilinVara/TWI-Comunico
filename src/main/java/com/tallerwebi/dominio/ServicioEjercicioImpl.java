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
    public void guardarEjercicio(EjercicioTraduccion ejercicioTraduccion) {

        this.repositorioEjercicio.guardar(ejercicioTraduccion);
    }

    @Override
    public Ejercicio obtenerEjercicio(Long ejercicioId) {
        return this.repositorioEjercicio.buscarEjercicio(ejercicioId);
    }

    @Override
    public Boolean resolverEjercicioTraduccion(EjercicioTraduccion ejercicioTraduccion, Long opcionId) {
        return ejercicioTraduccion.getOpcionCorrecta().getId().equals(opcionId);
    }

    @Override
    public Boolean resolverEjercicioMatriz(String puntosSeleccionados, String puntosDeLaMatriz) {
        return puntosSeleccionados.equals(puntosDeLaMatriz);
    }


}

