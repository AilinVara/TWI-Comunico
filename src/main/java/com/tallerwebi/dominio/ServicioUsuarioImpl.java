package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("servicioUsuario")
@Transactional
public class ServicioUsuarioImpl implements ServicioUsuario {

    private final RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioUsuarioImpl (RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void modificar(Usuario usuario) {
        this.repositorioUsuario.modificar(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }

    @Override
    public Set<Usuario> listar(Usuario usuario) {
        List<Usuario> usuarios = repositorioUsuario.buscarTodosLosUsuarios(usuario);
        Set<Usuario> usuariosAux = new HashSet<>();

        Set<Usuario> amigosSet = repositorioUsuario.buscarAmigos(usuario);

        for (Usuario usuarioAux : usuarios) {
            if (usuarioAux != null && !amigosSet.contains(usuarioAux)) {
                usuariosAux.add(usuarioAux);
            }
        }

        return usuariosAux;

    }

    @Override
    public List<Usuario> mostrarUsuarios() {
        return repositorioUsuario.buscarUsuarios();
    }

    @Override
    public List<Usuario> mostrarAdmins() {
        return repositorioUsuario.buscarAdmins();
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        repositorioUsuario.eliminarUsuario(usuario);
    }

    @Override
    public void eliminarRelacionesDeAmistad(Usuario usuario) {
        repositorioUsuario.eliminarRelacionesDeAmistad(usuario);
    }

    @Override
    @Transactional
    public void actualizarComunicoPointsUsuario(Long usuarioId, Integer nuevaCantidad) {
        Usuario usuario = buscarUsuarioPorId(usuarioId);
        if (usuario != null) {
            usuario.setComunicoPoints(nuevaCantidad);
            repositorioUsuario.guardar(usuario);
        }
    }
    }
