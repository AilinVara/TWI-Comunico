package com.tallerwebi.dominio;

public interface ServicioEjercicio {

    void guardarEjercicio(Ejercicio ejercicio);

    Ejercicio obtenerEjercicio(Long ejercicioId);

    Boolean resolverEjercicio(Ejercicio ejercicio, Long opcionId);

    public Boolean perderVida(Long id);

}
