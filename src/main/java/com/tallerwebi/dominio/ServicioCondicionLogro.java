package com.tallerwebi.dominio;

public interface ServicioCondicionLogro {
    void agregarCondicion(CondicionLogro condicionLogro);
    void guardar(CondicionLogro condicionLogro);
    CondicionLogro buscarCondicionLogroPorSuId(Long id);
}
