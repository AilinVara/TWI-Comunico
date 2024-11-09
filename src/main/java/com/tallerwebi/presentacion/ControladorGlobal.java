package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.time.LocalDateTime;

@ControllerAdvice
public class ControladorGlobal {

    private final ServicioUsuario servicioUsuario;
    private final ServicioExperiencia servicioExperiencia;
    private final ServicioVida servicioVida;
    private final ServicioTitulo servicioTitulo;


    //Commit
    @Autowired
    public ControladorGlobal(ServicioUsuario servicioUsuario, ServicioExperiencia servicioExperiencia, ServicioVida servicioVida, ServicioTitulo servicioTitulo){
        this.servicioUsuario = servicioUsuario;
        this.servicioExperiencia = servicioExperiencia;
        this.servicioVida = servicioVida;
        this.servicioTitulo = servicioTitulo;
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
            return 0;
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

    @ModelAttribute("titulo")
    public String obtenerTituloUsuario(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return "usuarioNull";
        }
        servicioTitulo.actualizarTituloSegunExperiencia(usuarioId);
        servicioTitulo.obtenerVidasYComunicoPointsCuandoConsigueTitulo(usuarioId);
        return servicioTitulo.obtenerTitulo(usuarioId);
    }

    @RequestMapping("/titulosUsuario")
    public ModelAndView mostrarTitulos() {
        return new ModelAndView("titulosUsuario");
    }

    private Long calcularTiempoRestante(Long usuarioId) {
        Vida vida = servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        return 120 - (duracion.getSeconds() % 120);
    }
}
