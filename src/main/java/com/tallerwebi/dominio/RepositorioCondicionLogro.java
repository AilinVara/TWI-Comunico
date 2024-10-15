package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCondicionLogro {
    void guardar(CondicionLogro condicionLogro);
    CondicionLogro buscarCondicion(Long id);
    void actualizar(CondicionLogro condicionLogro);
    List<CondicionLogro> buscarCondiciones();

}
