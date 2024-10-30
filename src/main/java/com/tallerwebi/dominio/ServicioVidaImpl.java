package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;


@Service("servicioVida")
@Transactional
@EnableScheduling
public class ServicioVidaImpl implements ServicioVida {
    //Commit
    private RepositorioVida repositorioVida;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioVidaImpl(RepositorioVida repositorioVida, RepositorioUsuario repositorioUsuario) {
        this.repositorioVida = repositorioVida;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public Vida obtenerVida(Long usuarioId) {
        return this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getVida();

//        //Esto es para guardar una vida temporalmente sin crearla en la base
//        if (vida == null) {
//            vida = new Vida();
//            vida.setCantidadDeVidasActuales(5);
//            vida.setId(vidaId);
//            this.repositorioVida.guardarUnaVida(vida);
//        }
//        return vida;
    }

    @Override
    public Boolean perderUnaVida(Long usuarioId) {
        Boolean perdida = Boolean.FALSE;
        Vida vida = this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getVida();

        if (vida.getCantidadDeVidasActuales() > 0) {
            vida.setCantidadDeVidasActuales(vida.getCantidadDeVidasActuales() - 1);

            repositorioVida.actualizarVida(vida);

            perdida = Boolean.TRUE;
        }

        return perdida;
    }

    @Override
    @Scheduled(fixedRate = 60000)
    public void regenerarVidasDeTodosLosUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.obtenerTodosLosUsuarios();

        for (Usuario usuario : usuarios) {
            Vida vida = usuario.getVida();
            LocalDateTime ahora = LocalDateTime.now();
            Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);


            if (duracion.toMinutes() >= 1 && vida.getCantidadDeVidasActuales() < 5) {
                vida.setCantidadDeVidasActuales(vida.getCantidadDeVidasActuales() + 1);
                vida.setUltimaVezQueSeRegeneroLaVida(ahora);
                repositorioVida.actualizarVida(vida);
            }
        }
    }

}



