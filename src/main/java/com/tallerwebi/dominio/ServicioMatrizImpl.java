package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioMatriz")
@Transactional
public class ServicioMatrizImpl implements ServicioMatriz {

    private RepositorioMatriz repositorioMatriz;

    @Autowired
    public ServicioMatrizImpl (RepositorioMatriz repositorioMatriz){ this.repositorioMatriz = repositorioMatriz;}

    @Override
    public EjercicioMatriz obtenerMatriz(Long matrizId){
        return this.repositorioMatriz.buscarMatriz(matrizId);
    }

}
