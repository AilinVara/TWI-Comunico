package com.tallerwebi.dominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioTitulo")
@Transactional
public class ServicioTituloImpl implements ServicioTitulo{

    private RepositorioUsuario repositorioUsuario;
    private RepositorioVida repositorioVida;

    @Autowired
    public ServicioTituloImpl(RepositorioUsuario repositorioUsuario, RepositorioVida repositorioVida) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioVida = repositorioVida;
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
        } else if (experiencia >= 3500) {
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
    @Override
    public void obtenerVidasYComunicoPointsCuandoConsigueTitulo(Long usuarioId){

        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
        Vida vida = repositorioVida.buscarUnaVidaPorId(usuarioId);
        Integer comunicoPoints = usuario.getComunicoPoints();
        String titulo = usuario.getTitulo();


        switch (titulo) {
            case "Novato":
                usuario.setComunicoPoints(usuario.getComunicoPoints() + 20);
                break;
            case "Amateur":
                usuario.setComunicoPoints(usuario.getComunicoPoints() + 40);
                break;
            case "Experto ":
                usuario.setComunicoPoints(usuario.getComunicoPoints() + 60);
                break;
            case "Comunicador ":
                usuario.setComunicoPoints(usuario.getComunicoPoints() + 100);
                break;
            default:

        }
    }
}
