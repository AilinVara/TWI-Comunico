package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

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
    public Boolean resolverEjercicioTraduccion(EjercicioTraduccion ejercicioTraduccion, Long opcionId) {
        return ejercicioTraduccion.getOpcionCorrecta().getId().equals(opcionId);
    }

    @Override
    public Boolean resolverEjercicioTraduccionSenia(EjercicioTraduccionSenia ejercicioTraduccionSenia, Long opcionId) {
        return ejercicioTraduccionSenia.getOpcionCorrecta().getId().equals(opcionId);
    }

    @Override
    public Boolean resolverEjercicioMatriz(String puntosSeleccionados, String puntosDeLaMatriz) {
        return puntosSeleccionados.equals(puntosDeLaMatriz);
    }

    @Override
    public List<String> convertirLetrasALista(String letras) {
        return Arrays.asList(letras.split(","));
    }

    @Override
    public Boolean resolverEjercicioFormaPalabras(String letras, String listaLetras) {
        return letras.equals(listaLetras);
    }

}

