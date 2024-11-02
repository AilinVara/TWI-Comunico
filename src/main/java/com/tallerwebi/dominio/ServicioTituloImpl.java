package com.tallerwebi.dominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioTitulo")
@Transactional
public class ServicioTituloImpl implements ServicioTitulo{

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioTituloImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public String obtenerTitulo(Long usuarioId) {
        return this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getTitulo();
    }


    @Override
    public void actualizarTituloSegunExperiencia(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
        int experiencia = usuario.getExperiencia().getCantidadExperiencia();

        String nuevoTitulo = "Principiante";
        if (experiencia >= 5000) {
            nuevoTitulo = "Comunicador";
        } else if (experiencia >= 3000) {
            nuevoTitulo = "Experto";
        } else if (experiencia >= 2000) {
            nuevoTitulo = "Amateur";
        } else if(experiencia >= 500) {
            nuevoTitulo = "Novato";
        }

        if (!nuevoTitulo.equals(usuario.getTitulo())) {
            usuario.setTitulo(nuevoTitulo);
            repositorioUsuario.guardar(usuario);
        }
    }

}
