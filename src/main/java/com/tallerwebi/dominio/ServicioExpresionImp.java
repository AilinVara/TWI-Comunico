package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
@Transactional
public class ServicioExpresionImp implements ServicioExpresion {

    private RepositorioExpresion repoExpresion;

    @Autowired
    public ServicioExpresionImp(RepositorioExpresion repoExpresion) {
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
    public void guardarExpresionSenias(String nombre, Path rutaImagen) throws IOException {
        ExpresionSenias expresion = new ExpresionSenias();
        expresion.setNombre(nombre);
        expresion.setImagenExpresion(Files.readAllBytes(rutaImagen));
        repoExpresion.guardar(expresion);
    }

}
