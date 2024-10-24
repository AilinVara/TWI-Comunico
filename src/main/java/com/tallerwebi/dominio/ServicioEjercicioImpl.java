package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioEjercicio")
@Transactional
public class ServicioEjercicioImpl implements ServicioEjercicio {

    private RepositorioEjercicioTraduccion repositorioEjercicioTraduccion;
    @Autowired
    private ServicioVida servicioVida;
    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioEjercicioImpl(RepositorioEjercicioTraduccion repositorioEjercicioTraduccion) {
        this.repositorioEjercicioTraduccion = repositorioEjercicioTraduccion;
    }

    @Override
    public void guardarEjercicio(EjercicioTraduccion ejercicioTraduccion) {

        this.repositorioEjercicioTraduccion.guardar(ejercicioTraduccion);
    }

    @Override
    public Ejercicio obtenerEjercicio(Long ejercicioId) {
        return this.repositorioEjercicioTraduccion.buscarEjercicio(ejercicioId);
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

