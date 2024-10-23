package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
