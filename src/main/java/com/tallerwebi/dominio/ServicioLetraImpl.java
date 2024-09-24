package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioLetra")
@Transactional
public class ServicioLetraImpl implements ServicioLetra {

    private RepositorioLetra repositorioLetra;

    @Autowired
    public ServicioLetraImpl(RepositorioLetra repositorioLetra){this.repositorioLetra = repositorioLetra;}


    @Override
    public Letra buscarPorNombre(String nombre) {
        return repositorioLetra.buscarUnaLetra(nombre);
    }

    @Override
    public List<Letra> buscarTodasLasLetras() {
        return repositorioLetra.buscarLetras();
    }
}
