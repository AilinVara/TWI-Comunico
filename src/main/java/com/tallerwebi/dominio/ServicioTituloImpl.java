package com.tallerwebi.dominio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioTitulo")
@Transactional
public class ServicioTituloImpl implements ServicioTitulo {

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
        } else if (experiencia >= 500) {
            nuevoTitulo = "Novato";
        }


        if (!nuevoTitulo.equals(usuario.getTitulo())) {
            usuario.setTitulo(nuevoTitulo);
            repositorioUsuario.guardar(usuario);
        }

    }

    @Override
    public void obtenerComunicoPointsCuandoConsigueTitulo(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
        Integer comunicoPoints = usuario.getComunicoPoints();
        String tituloActual = usuario.getTitulo().trim();

        if (!tituloActual.equals(usuario.getUltimoTitulo())) {

            switch (tituloActual) {
                case "Novato":
                    usuario.setComunicoPoints(comunicoPoints + 20);
                    break;
                case "Amateur":
                    usuario.setComunicoPoints(comunicoPoints + 40);
                    break;
                case "Experto":
                    usuario.setComunicoPoints(comunicoPoints + 60);
                    break;
                case "Comunicador":
                    usuario.setComunicoPoints(comunicoPoints + 100);
                    break;
                default:

                    break;
            }

            usuario.setUltimoTitulo(tituloActual);
            repositorioUsuario.guardar(usuario);

        }

    }
    @Override
    public int obtenerTiempoRegeneracionPorTitulo(Long usuarioId) {
        Usuario usuario = repositorioUsuario.buscarUsuarioPorId(usuarioId);
        String titulo = usuario.getTitulo().trim();

        // 120 minutos (2 horas) para "Principiante", reduzco 10 minutos por cada título
        switch (titulo) {
            case "Comunicador":
                return 90; // 120 - 30 minutos
            case "Experto":
                return 100; // 120 - 20 minutos
            case "Amateur":
                return 110; // 120 - 10 minutos
            case "Novato":
                return 120; // No se reduce
            default:
                return 120; // Default (Principiante)
        }
    }
    }


