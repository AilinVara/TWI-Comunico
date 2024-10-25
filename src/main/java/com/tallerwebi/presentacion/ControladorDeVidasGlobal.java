package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioVida;
import com.tallerwebi.dominio.Vida;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ModelAttribute;


import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;


@ControllerAdvice
public class ControladorDeVidasGlobal {

    private final ServicioVida servicioVida;



    @Autowired
    public ControladorDeVidasGlobal(ServicioVida servicioVida) {
        this.servicioVida = servicioVida;
    }

    @ModelAttribute("vidas")
    public Integer obtenerVidasUsuario(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0;
        }
        return servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
    }

    @ModelAttribute("tiempoRestante")
    public Long obtenerTiempoRestante(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0L;
        }
        return calcularTiempoRestante(usuarioId);
    }


    private Long calcularTiempoRestante(Long usuarioId) {
        Vida vida = servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        return 120 - (duracion.getSeconds() % 120);
    }


}

