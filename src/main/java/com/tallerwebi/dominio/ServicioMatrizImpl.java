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
    public Matriz obtenerMatriz(Long matrizId){
        return this.repositorioMatriz.buscarMatriz(matrizId);
    }

    @Override
    public Boolean resolverMatriz(String puntosSeleccionados, String puntosDeLaMatriz) {
        return puntosSeleccionados.equals(puntosDeLaMatriz);
    }

    @Override
    public Matriz obtenerMatrizPorEjercicio(Long ejercicioId) {
        return this.repositorioMatriz.buscarMatrizPorEjercicio(ejercicioId);
    }
}
