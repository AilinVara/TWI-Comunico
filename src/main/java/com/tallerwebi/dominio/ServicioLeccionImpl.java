package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioLeccion")
@Transactional
public class ServicioLeccionImpl implements ServicioLeccion {

    private RepositorioLeccion repositorioLeccion;
    @Autowired
    public ServicioLeccionImpl(RepositorioLeccion repositorioLeccion) {
        this.repositorioLeccion = repositorioLeccion;
    }

    @Override
    public void guardarLeccion(Leccion leccion) {
        this.repositorioLeccion.guardar(leccion);
    }

    @Override
    public Leccion obtenerLeccion(Long id) {
        return this.repositorioLeccion.buscarPorId(id);
    }

    @Override
    public List<Leccion> obtenerLeccionesPorTipo(String tipo) {
        return this.repositorioLeccion.buscarPorTipo(tipo);
    }
}
