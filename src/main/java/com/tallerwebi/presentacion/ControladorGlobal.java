package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioExperiencia;
import com.tallerwebi.dominio.ServicioUsuario;;
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
    private final ServicioExperiencia servicioExperiencia;

    private final ServicioVida servicioVida;
    //Commit
    @Autowired
    public ControladorGlobal(ServicioUsuario servicioUsuario, ServicioExperiencia servicioExperiencia){
        this.servicioUsuario = servicioUsuario;
        this.servicioExperiencia = servicioExperiencia;
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
    @ModelAttribute("experiencia")
    public Integer obtenerExperienciaDelUsuario(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0; // Retorna un valor por defecto si no hay usuario logueado
        }
        return servicioExperiencia.obtenerExperiencia(usuarioId).getCantidadExperiencia();
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
