package com.tallerwebi.dominio;

public interface ServicioEjercicio {
    Ejercicio obtenerEjercicio(Long ejercicioId);

    Boolean resolverEjercicio(Ejercicio ejercicio, Long opcionId);
}
