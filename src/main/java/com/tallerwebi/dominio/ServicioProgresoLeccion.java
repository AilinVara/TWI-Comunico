package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProgresoLeccion {
    void guardarProgresoLeccion(ProgresoLeccion progresoLeccion);
    ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId);
    List<ProgresoLeccion> buscarPorUsuarioIdYLeccionId(Long usuarioId, Long leccionId);

}
