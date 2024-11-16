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

    @ModelAttribute("ayudas")
    public Integer obtenerAyudas(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0;
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);

        // Actualiza la sesión si es necesario
        if (request.getSession().getAttribute("ayudas") == null ||
                !request.getSession().getAttribute("ayudas").equals(usuario.getAyudas())) {
            request.getSession().setAttribute("ayudas", usuario.getAyudas());
        }

        return usuario.getAyudas();
    }

    @ModelAttribute("llaves")
    public Integer obtenerLlaves(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0;
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);

        // Actualiza la sesión si es necesario
        if (request.getSession().getAttribute("llaves") == null ||
                !request.getSession().getAttribute("llaves").equals(usuario.getLlaves())) {
            request.getSession().setAttribute("llaves", usuario.getLlaves());
        }

        return usuario.getLlaves();
    }

    @ModelAttribute("experiencia")
    public Integer obtenerExperienciaDelUsuario(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0;
        }
        return servicioExperiencia.obtenerExperiencia(usuarioId).getCantidadExperiencia();
    }

    @ModelAttribute("experienciaMaxima")
    public Integer obtenerExperienciaMaximaDelUsuario(HttpServletRequest request) {

        Long usuarioId = (Long) request.getSession().getAttribute("id");
        if (usuarioId == null) {
            return 0;
        }
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        String tituloActual = usuario.getTitulo();
        return servicioTitulo.obtenerExperienciaMaximaPorTitulo(tituloActual);
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

    @ModelAttribute("suscripcion")
    public String obtenerSuscripcionDelUsuario(HttpServletRequest request) {
        Long usuarioId = (Long) request.getSession().getAttribute("id");

        if (usuarioId == null) {
            return "";
        }

        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);
        String suscripcion = usuario.getSuscripcion().getTipoSuscripcion().getNombre();

        if(suscripcion == null){
            suscripcion = "sin plan";
        }

        return suscripcion;
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
        long tiempoRegeneracionEnMinutos = 120; // 2 horas por defecto

        switch (titulo) {
            case "Novato":
                tiempoRegeneracionEnMinutos = 115;  // 1 hora 55 minutos
                break;
            case "Amateur":
                tiempoRegeneracionEnMinutos = 110;  // 1 hora 50 minutos
                break;
            case "Experto":
                tiempoRegeneracionEnMinutos = 100;  // 1 hora 40 minutos
                break;
            case "Comunicador":
                tiempoRegeneracionEnMinutos = 90;   // 1 hora 30 minutos
                break;
            default:
                break;
        }

        long minutosDesdeUltimaRegeneracion = duracion.toMinutes();

        long tiempoRestanteEnMinutos = tiempoRegeneracionEnMinutos - minutosDesdeUltimaRegeneracion;

        // Asegurarse de que el tiempo restante no sea negativo
        tiempoRestanteEnMinutos = Math.max(tiempoRestanteEnMinutos, 0);

        return tiempoRestanteEnMinutos;
    }
}
