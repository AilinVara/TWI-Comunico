package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class GestionarVidas {

    private final ServicioVida servicioVida;

    @Autowired
    public GestionarVidas(ServicioVida servicioVida) {
        this.servicioVida = servicioVida;
    }

    public void perderVidaSiIncorrecto(Long usuarioId, Boolean resuelto) {
        if (!resuelto) {
            servicioVida.perderUnaVida(usuarioId);

        }
    }

    public void agregarTiempoRestanteAlModelo(ModelMap modelo, Long usuarioId) {
        Vida vida = servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
        long tiempoRestante = 120 - (segundosDesdeUltimaRegeneracion % 120);
        modelo.put("tiempoRestante", tiempoRestante);
    }
}
