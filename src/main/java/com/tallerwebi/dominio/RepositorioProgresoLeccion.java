package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioProgresoLeccion {
    void guardar(ProgresoLeccion progresoLeccion);

    ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId);

    List<ProgresoLeccion> buscarPorUsuarioIdYLeccionId(Long usuarioId, Long leccionId);

    void actualizar(ProgresoLeccion progreso);

    List<ProgresoLeccion> buscarProgresosPorUsuario(Long usuarioId);

    List<Leccion> darmeTodasLasLecciones();
}
