package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ServicioPerfilUsuarioImpl implements ServicioPerfilUsuario {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioSuscripcion repositorioSuscripcion;

    @Autowired
    public ServicioPerfilUsuarioImpl(RepositorioUsuario repositorioUsuario, RepositorioSuscripcion repositorioSuscripcion) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioSuscripcion = repositorioSuscripcion;
    }

    public Usuario buscarUsuario(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    public Usuario buscarUsuarioPorId(Long id) {
            return repositorioUsuario.buscarUsuarioPorId(id);
        }

    @Override
    public void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto) {

        if (!foto.isEmpty()) {
            try {
                // Obtener la ruta del directorio actual y construir la ruta absoluta
                String currentDir = System.getProperty("user.dir");
                String imagesDir = currentDir + "/src/main/webapp/resources/core/images/";

                // Crear los directorios si no existen
                Path rutaAbsoluta = Paths.get(imagesDir + foto.getOriginalFilename());
                Files.createDirectories(rutaAbsoluta.getParent());

                // Escribir el archivo
                byte[] bytes = foto.getBytes();
                Files.write(rutaAbsoluta, bytes);

                // Actualizar la información del usuario
                usuarioExistente.setFoto(foto.getOriginalFilename());
                repositorioUsuario.modificar(usuarioExistente);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        usuarioExistente.setDescripcion(usuario.getDescripcion());
        usuarioExistente.setNombreDeUsuario(usuario.getNombreDeUsuario());
        repositorioUsuario.modificar(usuarioExistente);
    }

    @Override
    public List<Compra> historialDeCompras(Usuario usuario) {
        return repositorioUsuario.historialDeCompras(usuario);
    }

    @Override
    public Set<Usuario> buscarAmigos(Usuario usuario) {
            return repositorioUsuario.buscarAmigos(usuario);
        }

    @Override
    public Boolean verificarPlan(Usuario usuario) {
            return repositorioSuscripcion.verificarSuscripcionDeUsuario(usuario)!=null;
        }
}