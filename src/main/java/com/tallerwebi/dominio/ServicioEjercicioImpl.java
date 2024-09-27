package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioEjercicio")
@Transactional
public class ServicioEjercicioImpl implements ServicioEjercicio{

    private RepositorioEjercicio repositorioEjercicio;

    @Autowired
    public ServicioEjercicioImpl (RepositorioEjercicio repositorioEjercicio){ this.repositorioEjercicio = repositorioEjercicio;}

    @Override
    public void guardarEjercicio(Ejercicio ejercicio) {
        this.repositorioEjercicio.guardar(ejercicio);
    }

    @Override
    public Ejercicio obtenerEjercicio(Long ejercicioId){
        return this.repositorioEjercicio.buscarEjercicio(ejercicioId);
    }

    @Override
    public Boolean resolverEjercicio(Ejercicio ejercicio, Long opcionId) {
        return ejercicio.getOpcionCorrecta().getId().equals(opcionId);
    }

}

