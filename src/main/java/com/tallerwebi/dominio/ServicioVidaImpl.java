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
    private ServicioTitulo servicioTitulo;

    @Autowired
    public ServicioVidaImpl(RepositorioVida repositorioVida, RepositorioUsuario repositorioUsuario, ServicioTitulo servicioTitulo) {
        this.repositorioVida = repositorioVida;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioTitulo = servicioTitulo;
    }


    @Override
    public Vida obtenerVida(Long usuarioId) {
        return this.repositorioUsuario.buscarUsuarioPorId(usuarioId).getVida();

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
    @Scheduled(fixedRate = 5400000) //Se ejecuta cada una hora y media
    public void regenerarVidasDeTodosLosUsuarios() {
        List<Usuario> usuarios = repositorioUsuario.buscarTodos();

        for (Usuario usuario : usuarios) {
            Vida vida = usuario.getVida();
            LocalDateTime ahora = LocalDateTime.now();

            int tiempoRegeneracion = servicioTitulo.obtenerTiempoRegeneracionPorTitulo(usuario.getId());

            Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);

            if (duracion.toMinutes() >= tiempoRegeneracion && vida.getCantidadDeVidasActuales() < 5) {
                vida.setCantidadDeVidasActuales(vida.getCantidadDeVidasActuales() + 1);
                vida.setUltimaVezQueSeRegeneroLaVida(ahora);
                repositorioVida.actualizarVida(vida);
            }
        }
    }

}



