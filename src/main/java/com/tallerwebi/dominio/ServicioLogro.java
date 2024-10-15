package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioLogro {

    List<Logro> obtenerTodosLosLogros();
    Logro obtenerLogroPorId(Long id);
    void guardarLogro(Logro logro);
    void desbloquearLogro(Long logroId, Long condicionId);
    List<Logro> buscarLogrosPorNombre(String nombre);
    List<Logro> buscarLogrosSolamenteDesbloqueados();

}
