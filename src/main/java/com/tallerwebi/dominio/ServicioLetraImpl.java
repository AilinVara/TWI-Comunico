package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Service("servicioLetra")
@Transactional
public class ServicioLetraImpl implements ServicioLetra {

    private RepositorioLetra repositorioLetra;

    @Autowired
    public ServicioLetraImpl(RepositorioLetra repositorioLetra){this.repositorioLetra = repositorioLetra;}


    @Override
    public Letra buscarPorNombre(String nombre) {
        return repositorioLetra.buscarUnaLetra(nombre);
    }

    @Override
    public List<Letra> buscarTodasLasLetras() {
        return repositorioLetra.buscarLetras();
    }

    @Override
    public void crearTodasLasLetras() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        for(char l = 'a'; l <= 'z'; l++){
            File archivoImagenSenias = new File("src/main/webapp/resources/core/img/senias-" + l + ".png");
            File archivoImagenBraille = new File("src/main/webapp/resources/core/img/braille-" + l + ".png");

            byte[] imagenBraille = Files.readAllBytes(archivoImagenBraille.toPath());
            byte[] imagenSenias = Files.readAllBytes(archivoImagenSenias.toPath());

            Letra letra = new Letra(String.valueOf(l), imagenSenias, imagenBraille);
            repositorioLetra.guardar(letra);
        }
    }
}
