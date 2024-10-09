package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.List;

public interface RepositorioVida {

    public void guardarUnaVida(Vida vida);
    public Vida buscarUnaVidaPorId(Long id);

}
