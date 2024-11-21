package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;


@Controller
public class ControladorEjercicio {
    //Commit
    private final ServicioEjercicio servicioEjercicio;
    private final ServicioLeccion servicioLeccion;
    private final ServicioProgresoLeccion servicioProgresoLeccion;
    private final ServicioVida servicioVida;
    private final ServicioExperiencia servicioExperiencia;
    private final ServicioUsuario servicioUsuario;
    private final ServicioTitulo servicioTitulo;


    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida, ServicioExperiencia servicioExperiencia, ServicioTitulo servicioTitulo, ServicioUsuario servicioUsuario) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
        this.servicioExperiencia = servicioExperiencia;
        this.servicioTitulo = servicioTitulo;
        this.servicioUsuario = servicioUsuario;

    }

    @RequestMapping(value = "/ejercicio/{indice}", method = RequestMethod.GET)
    public ModelAndView irAjercicio(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Integer vidas = this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
        modelo.put("leccion", leccionId);
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Ejercicio ejercicio = leccion.getEjercicios().get(indice - 1);
        modelo.put("vidas", vidas);
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);
        agregarTiempoRestanteAlModelo(modelo,usuarioId);
        if(leccion.getTipo().equals("combinado")){
            modelo.put("combinado", true);
        }

        Map<Class<? extends Ejercicio>, String> redirecciones = Map.of(
                EjercicioTraduccion.class, "redirect:/braille/lecciones/traduccion",
                EjercicioMatriz.class, "redirect:/braille/lecciones/matriz",
                EjercicioFormaPalabra.class, "redirect:/braille/lecciones/forma-palabras",
                EjercicioTraduccionSenia.class, "redirect:/senias"
        );


        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);

        if (vidas == 0 && !usuario.getSuscripcion().getTipoSuscripcion().getNombre().equals("premium")) {
            if (leccion.getTipo().equals("combinado")) {
                return new ModelAndView("redirect:/braille/lecciones/combinado");
            }
            String redireccion = redirecciones.get(ejercicio.getClass());
            if (redireccion != null) {
                return new ModelAndView(redireccion);
            }
        }

        if (ejercicio instanceof EjercicioTraduccion) {
            EjercicioTraduccion ejercicioTraduccion = (EjercicioTraduccion) ejercicio;

            Set<Opcion> opciones = ejercicioTraduccion.getOpcionesIncorrectas();
            opciones.add(ejercicioTraduccion.getOpcionCorrecta());

            List<Opcion> opcionesDesordenadas = new ArrayList<>(opciones);
            Collections.shuffle(opcionesDesordenadas);

            modelo.put("opciones", opcionesDesordenadas);
            return new ModelAndView("ejercicio", modelo);
        }else if(ejercicio instanceof EjercicioMatriz)
            return new ModelAndView("formaLetras", modelo);
        else if(ejercicio instanceof EjercicioFormaPalabra){
            EjercicioFormaPalabra ejercicioFormaPalabra = (EjercicioFormaPalabra) ejercicio;
            List<String> letras = servicioEjercicio.convertirLetrasALista(ejercicioFormaPalabra.getLetras());
            modelo.put("letras", letras);
            return new ModelAndView("ejercicios-forma-palabra", modelo);
        } else {
            return new ModelAndView("ejercicio-video", modelo);
        }
    }

    @RequestMapping(path = "/resolver/{indice}", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@PathVariable("indice") Long indice, @RequestParam("respuesta") String respuesta, @RequestParam("ejercicioId") Long ejercicioId,
                                          @RequestParam("leccion") Long leccionId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Usuario usuario = servicioUsuario.buscarUsuarioPorId(usuarioId);

        ProgresoLeccion progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        Boolean resuelto;
        if (progreso == null) {
            this.servicioProgresoLeccion.crearProgresoLeccion(leccionId, usuarioId);
            progreso = this.servicioProgresoLeccion.buscarPorIds(leccionId, usuarioId, ejercicioId);
        }

        if(ejercicio instanceof EjercicioTraduccion){
            resuelto = this.servicioEjercicio.resolverEjercicioTraduccion((EjercicioTraduccion) ejercicio, Long.parseLong(respuesta));

            mav.setViewName("ejercicio");
        }else if(ejercicio instanceof EjercicioMatriz){
            EjercicioMatriz ejercicioMatriz = (EjercicioMatriz) ejercicio;
            resuelto = this.servicioEjercicio.resolverEjercicioMatriz(respuesta, ejercicioMatriz.getPuntos());
            mav.setViewName("formaLetras");
        }else if(ejercicio instanceof  EjercicioFormaPalabra){
            EjercicioFormaPalabra ejercicioFormaPalabra = (EjercicioFormaPalabra) ejercicio;
            resuelto = this.servicioEjercicio.resolverEjercicioFormaPalabras(ejercicioFormaPalabra.getRespuestaCorrecta(), respuesta);
            mav.setViewName("ejercicios-forma-palabra");
        }else{
            resuelto = this.servicioEjercicio.resolverEjercicioTraduccionSenia((EjercicioTraduccionSenia) ejercicio, Long.parseLong(respuesta));
            mav.setViewName("ejercicio-video");
        }
        if (!resuelto && !usuario.getSuscripcion().getTipoSuscripcion().getNombre().equals("premium")) {
            Boolean vidaPerdida = this.servicioVida.perderUnaVida(usuarioId);
            if (vidaPerdida) {
                // Obtener el nuevo número de vidas y actualizar la sesión
                Integer nuevasVidas = this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
                request.getSession().setAttribute("vidasNumero", nuevasVidas);
            }
            this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);
        }


        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

        if(leccion.getTipo().equals("combinado")){
            modelo.put("combinado", true);
        }
        modelo.put("vidas", this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales());
        agregarTiempoRestanteAlModelo(modelo,usuarioId);
        modelo.put("indice", indice);
        modelo.put("leccion", leccionId);
        modelo.put("ejercicio", ejercicio);
        modelo.put("esCorrecta", resuelto);
        mav.addAllObjects(modelo);
        return mav;
    }

    @RequestMapping(value = "/ejercicio/{indice}/ayuda")
    public ModelAndView usarAyuda(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Integer vidas = this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
        modelo.put("leccion", leccionId);
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);

        if(leccion.getTipo().equals("combinado")){
            modelo.put("combinado", true);
        }

        Ejercicio ejercicio = leccion.getEjercicios().get(indice - 1);
        modelo.put("vidas", vidas);
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);

        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(usuarioId);
        usuario.setAyudas(usuario.getAyudas()-1);
        this.servicioUsuario.modificar(usuario);

        request.getSession().setAttribute("ayudas", usuario.getAyudas());
        modelo.put("ayudas", usuario.getAyudas());

        if (ejercicio instanceof EjercicioTraduccion) {
            EjercicioTraduccion ejercicioTraduccion = (EjercicioTraduccion) ejercicio;

            Set<Opcion> opciones = new HashSet<>();
            Set<Opcion> opcionesIncorrectas = ejercicioTraduccion.getOpcionesIncorrectas();

            Opcion opcionIncorrecta = opcionesIncorrectas.iterator().next();

            opciones.add(opcionIncorrecta);
            opciones.add(ejercicioTraduccion.getOpcionCorrecta());

            List<Opcion> opcionesDesordenadas = new ArrayList<>(opciones);
            Collections.shuffle(opcionesDesordenadas);

            modelo.put("opciones", opcionesDesordenadas);
            return new ModelAndView("ejercicio", modelo);
        } else if (ejercicio instanceof EjercicioMatriz) {
            EjercicioMatriz ejercicioMatriz = (EjercicioMatriz) ejercicio;

            String puntosLetra = ejercicioMatriz.getPuntos();
            int primerUno = puntosLetra.indexOf('1');

            modelo.put("punto", primerUno);
            return new ModelAndView("formaLetras", modelo);
        } else if (ejercicio instanceof EjercicioFormaPalabra) {
            EjercicioFormaPalabra ejercicioFormaPalabra = (EjercicioFormaPalabra) ejercicio;
            String respuestaCorrecta = ejercicioFormaPalabra.getRespuestaCorrecta();
            List<String> letrasListaOriginal = servicioEjercicio.convertirLetrasALista(ejercicioFormaPalabra.getLetras());

            List<String> letrasLista = new ArrayList<>();

            int contadorEliminados = 0;

            for (String letra : letrasListaOriginal) {
                if (!respuestaCorrecta.contains(letra) && contadorEliminados < 1) {
                    contadorEliminados++;
                } else {
                    letrasLista.add(letra);
                }
            }

            modelo.put("letras", letrasLista);
            return new ModelAndView("ejercicios-forma-palabra", modelo);
        }
        return new ModelAndView("ejercicio-video", modelo);
    }

    @RequestMapping(value = "/ejercicio-video", method = RequestMethod.GET)
    public ModelAndView irAEjercicioVideo(@RequestParam(required = false, defaultValue = "2", value = "id") Long id) {
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicioVideo);
        return new ModelAndView("ejercicio-video", modelo);
    }

    private void agregarTiempoRestanteAlModelo(ModelMap modelo, Long usuarioId) {
        Vida vida = this.servicioVida.obtenerVida(usuarioId);
        LocalDateTime ahora = LocalDateTime.now();

        // Obtener el tiempo de regeneración en minutos según el título del usuario
        int tiempoRegeneracionEnMinutos = servicioTitulo.obtenerTiempoRegeneracionPorTitulo(usuarioId);

        Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
        long minutosDesdeUltimaRegeneracion = duracion.toMinutes();

        // Calcular el tiempo restante basado en el tiempo de regeneración personalizado
        long tiempoRestanteEnMinutos = tiempoRegeneracionEnMinutos - minutosDesdeUltimaRegeneracion;

        // Asegurarse de que el tiempo restante no sea negativo
        tiempoRestanteEnMinutos = Math.max(tiempoRestanteEnMinutos, 0);

        modelo.put("tiempoRestante", tiempoRestanteEnMinutos);
    }


}

