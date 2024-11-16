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

//    @ModelAttribute
//    public void addAttributes(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Integer monedas = (Integer) session.getAttribute("points");
//            if (monedas != null) {
//                model.addAttribute("points", monedas);
//
//            }
//        }
//    }
@ModelAttribute("points")
public Integer obtenerComunicoPoints(HttpServletRequest request) {
    Long usuarioId = (Long) request.getSession().getAttribute("id");
    if (usuarioId == null) {
        return 0;
    }
    Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);

    // Actualiza la sesión si es necesario
    if (request.getSession().getAttribute("points") == null ||
            !request.getSession().getAttribute("points").equals(usuario.getComunicoPoints())) {
        request.getSession().setAttribute("points", usuario.getComunicoPoints());
    }

    return usuario.getComunicoPoints();
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
        return calcularTiempoRestante(request);
    }

    @ModelAttribute("titulo")
    public String obtenerTituloUsuario(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return "usuarioNull";
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        servicioTitulo.actualizarTituloSegunExperiencia(usuarioId);
        servicioTitulo.obtenerComunicoPointsCuandoConsigueTitulo(usuarioId);
        request.getSession().setAttribute("points", usuario.getComunicoPoints());
        servicioVida.regenerarVidasDeTodosLosUsuarios();
        return servicioTitulo.obtenerTitulo(usuarioId);
    }

    @RequestMapping("/titulosUsuario")
    public ModelAndView mostrarTitulos() {
        return new ModelAndView("titulosUsuario");
    }

    @ModelAttribute("tiempoRestante")
    public Long calcularTiempoRestante(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0L;
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            return 0L;
        }
        Vida vida = servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();
        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);

        String titulo = usuario.getTitulo().trim();
        long tiempoRegeneracion = 120; // (2 horas)

        switch (titulo) {
            case "Novato":
                tiempoRegeneracion = 115;  // 1 hora 55 minutos
                break;
            case "Amateur":
                tiempoRegeneracion = 110;  // 1 hora 50 minutos
                break;
            case "Experto":
                tiempoRegeneracion = 100;  // 1 hora 40 minutos
                break;
            case "Comunicador":
                tiempoRegeneracion = 90;   // 1 hora 30 minutos
                break;
            default:
                break;
        }

        return tiempoRegeneracion * 60 - (duracion.getSeconds() % (tiempoRegeneracion * 60));
    }
}

