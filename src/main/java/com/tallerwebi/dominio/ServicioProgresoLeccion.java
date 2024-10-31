package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioProgresoLeccion {
    void guardarProgresoLeccion(ProgresoLeccion progresoLeccion);

    ProgresoLeccion buscarPorIds(Long leccionId, Long usuarioId, Long ejercicioId);

    List<ProgresoLeccion> buscarProgresoLeccionDeUsuario(Long usuarioId, Long leccionId);

    void crearProgresoLeccion(Long leccionId, Long usuarioId);

    void actualizarProgreso(ProgresoLeccion progreso, Boolean resuelto);

    Boolean verificarCompletado(List<ProgresoLeccion> progresoLeccion);

    Boolean verificarCompletadoPorLeccion(Long leccionId, Long usuarioId);

    List<ProgresoLeccion> buscarProgresoPorTipoEjercicio(String tipoEjercicio, Long usuarioId);
}
