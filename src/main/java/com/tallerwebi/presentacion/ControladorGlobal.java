package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.ServicioVida;
import com.tallerwebi.dominio.Vida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControladorGlobal {

    private final ServicioUsuario servicioUsuario;
    private final ServicioVida servicioVida;
    //Commit
    @Autowired
    public ControladorGlobal(ServicioUsuario servicioUsuario,ServicioVida servicioVida){
        this.servicioUsuario = servicioUsuario;
        this.servicioVida = servicioVida;
    }

    @ModelAttribute
    public void addAttributes(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            Integer monedas = (Integer) session.getAttribute("points");
            if (monedas != null) {
                model.addAttribute("points", monedas);
            }
        }
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
