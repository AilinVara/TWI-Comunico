package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioEjercicio")
@Transactional
public class ServicioEjercicioImpl implements ServicioEjercicio{

    private RepositorioEjercicio repositorioEjercicio;

    @Autowired
    public ServicioEjercicioImpl (RepositorioEjercicio repositorioEjercicio){ this.repositorioEjercicio = repositorioEjercicio;}

    @Override
    public Ejercicio obtenerEjercicio(Long ejercicioId){
        return this.repositorioEjercicio.buscarUno(1L);
    }


}

