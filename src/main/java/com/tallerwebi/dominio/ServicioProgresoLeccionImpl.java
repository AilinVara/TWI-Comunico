package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("servicioProgresoLeccion")
@Transactional
public class ServicioProgresoLeccionImpl implements ServicioProgresoLeccion{
    private RepositorioProgresoLeccion repositorioProgresoLeccion;

    @Autowired
    public ServicioProgresoLeccionImpl(RepositorioProgresoLeccion repositorioProgresoLeccion) {
        this.repositorioProgresoLeccion = repositorioProgresoLeccion;
    }
    @Override
    public void guardarProgresoLeccion(ProgresoLeccion progresoLeccion) {
        this.repositorioProgresoLeccion.guardar(progresoLeccion);
    }

    @Override
    public ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId) {
        return this.repositorioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
    }

    @Override
    public List<ProgresoLeccion> buscarPorUsuarioIdYLeccionId(Long usuarioId, Long leccionId) {
        return this.repositorioProgresoLeccion.buscarPorUsuarioIdYLeccionId(usuarioId, leccionId);
    }
}
