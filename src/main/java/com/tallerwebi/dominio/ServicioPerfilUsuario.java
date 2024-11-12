package com.tallerwebi.dominio;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ServicioPerfilUsuario {
        Usuario buscarUsuario(String email,String password);
        void editarPerfilCompleto(Usuario usuarioExistente, Usuario usuario, MultipartFile foto);
        Usuario buscarUsuarioPorId(Long id);
        List<Compra> historialDeCompras(Usuario usuario);
        Set<Usuario> buscarAmigos(Usuario usuario);
        Boolean verificarPlan(Usuario usuario);
    }