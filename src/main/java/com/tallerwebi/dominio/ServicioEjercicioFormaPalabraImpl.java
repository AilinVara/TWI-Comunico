package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service ("servicioEjercicioFormaPalabra")
@Transactional
public class ServicioEjercicioFormaPalabraImpl implements ServicioEjercicioFormaPalabra {

    private RepositorioEjercicioFormaPalabra repositorioEjercicioFormaPalabra;

    @Autowired
    public ServicioEjercicioFormaPalabraImpl (RepositorioEjercicioFormaPalabra repositorioEjercicioFormaPalabra)
    { this.repositorioEjercicioFormaPalabra = repositorioEjercicioFormaPalabra;}

    @Override
    public List<String> convertirLetrasALista(String letras) {
        return Arrays.asList(letras.split(","));
    }

    @Override
    public EjercicioFormaPalabra obtenerEjercicio(Long id) {
        return this.repositorioEjercicioFormaPalabra.buscar(id);
    }

    @Override
    public Boolean resolverEjercicio(String letras, String listaLetras) {
        return letras.equals(listaLetras);
    }

}
