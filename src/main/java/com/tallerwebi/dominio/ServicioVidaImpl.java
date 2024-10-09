package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;


@Service("servicioVida")
@Transactional
public class ServicioVidaImpl implements ServicioVida {

    private RepositorioVida repositorioVida;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioVidaImpl(RepositorioVida repositorioVida, RepositorioUsuario repositorioUsuario) {
        this.repositorioVida = repositorioVida;
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public Vida obtenerVida(Long vidaId) {
        return this.repositorioVida.buscarUnaVidaPorId(vidaId);

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
    @Scheduled(fixedRate = 60000)//Es para que se regenere cada un minuto (60000 milisegundos)
    public Boolean regenerarUnaVida(Long vidaId) {
        Boolean vidaRegenerada = false;
        Vida vida = obtenerVida(vidaId);
        LocalDateTime ahora = LocalDateTime.now();
        //Compara la ultima vez que se regeneró la vida con la hora actual.
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        Integer cantidadDeVidasMaximas = 5;
//        System.out.println("Ultima vez que se regeneró la vida: " + vida.getUltimaVezQueSeRegeneroLaVida());
//        System.out.println("Hora actual: " + ahora);
        //Si la duracion pasada a minutos es mayor o igual a 1 minuto y la cantidad de vidas actuales es igual a 5(Max cantidad de vidas)
        if (duracion.toMinutes() >= 1 && vida.getCantidadDeVidasActuales() < cantidadDeVidasMaximas) {

            vida.setCantidadDeVidasActuales(vida.getCantidadDeVidasActuales() + 1);//Setea la cantidad de vidas actuales para que pasen a ser 1 mas

            vida.setUltimaVezQueSeRegeneroLaVida(ahora);//La vida se regenera en el tiempo actual

            repositorioVida.guardarUnaVida(vida);//Y la guarda en la bd
            vidaRegenerada = true;
        }


        return vidaRegenerada;

    }


}
