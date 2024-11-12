package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    Usuario buscar(String email);
    void modificar(Usuario usuario);
    Usuario buscarUsuarioPorId(Long id);
    List<Usuario> buscarTodos();
    List<Usuario> buscarTodosLosUsuarios(Usuario usuario);
    Set<Usuario> buscarAmigos(Usuario usuario);
    List<Usuario> buscarUsuarios();
    List<Usuario> buscarAdmins();
    void eliminarUsuario(Usuario usuario);
    void eliminarRelacionesDeAmistad(Usuario usuario);
    Usuario buscarPorToken(String token);
    List<Compra> historialDeCompras(Usuario usuario);
    Integer cantidadDeCompras(Usuario usuario, LocalDateTime fechaCompraPlan);
}

