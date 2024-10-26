package com.tallerwebi.dominio;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ServicioExpresion {

    ExpresionSenias buscarExpresionSenias(String nombre);

    List<ExpresionSenias> listarExpresionSenias();

    void guardarExpresionSenias(String nombre, Path rutaImagen) throws IOException;
}
