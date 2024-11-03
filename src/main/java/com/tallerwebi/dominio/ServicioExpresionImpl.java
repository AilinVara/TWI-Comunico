
package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
@Transactional
public class ServicioExpresionImpl implements ServicioExpresion {

    private RepositorioExpresion repoExpresion;

    @Autowired
    public ServicioExpresionImpl(RepositorioExpresion repoExpresion) {
        this.repoExpresion = repoExpresion;
    }

    @Override
    public ExpresionSenias buscarExpresionSenias(String nombre) {
        return this.repoExpresion.obtenerUnaExpresionPorNombre(nombre);
    }

    @Override
    public List<ExpresionSenias> listarExpresionSenias() {
        return this.repoExpresion.obtenerExpresiones();
    }

    @Override
    public void guardarExpresionSenias(String nombre, String rutaImagen) throws IOException {
        File fileExpresion = new File(rutaImagen);
        byte[] imagenExpresion = Files.readAllBytes(fileExpresion.toPath());

        ExpresionSenias expresion = new ExpresionSenias(nombre, imagenExpresion);
        repoExpresion.guardar(expresion);
    }

}
