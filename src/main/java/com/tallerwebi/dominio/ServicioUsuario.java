package com.tallerwebi.dominio;

import java.util.List;
import java.util.Set;

public interface ServicioUsuario {
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    Set<Usuario> listar(Usuario usuario);
    List<Usuario> mostrarUsuarios();
    List<Usuario> mostrarAdmins();
    void eliminarUsuario(Usuario usuario);
    void eliminarRelacionesDeAmistad(Usuario usuario);
}
