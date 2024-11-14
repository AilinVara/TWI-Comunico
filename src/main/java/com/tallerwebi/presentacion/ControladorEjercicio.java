package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private ServicioEjercicio servicioEjercicio;
    private ServicioLeccion servicioLeccion;
    private ServicioProgresoLeccion servicioProgresoLeccion;
    private ServicioVida servicioVida;
    private ServicioExperiencia servicioExperiencia;
    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioLeccion servicioLeccion, ServicioProgresoLeccion servicioProgresoLeccion, ServicioVida servicioVida, ServicioExperiencia servicioExperiencia, ServicioUsuario servicioUsuario) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioLeccion = servicioLeccion;
        this.servicioProgresoLeccion = servicioProgresoLeccion;
        this.servicioVida = servicioVida;
        this.servicioExperiencia = servicioExperiencia;
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

        Map<Class<? extends Ejercicio>, String> redirecciones = Map.of(
                EjercicioTraduccion.class, "redirect:/braille/lecciones/traduccion",
                EjercicioMatriz.class, "redirect:/braille/lecciones/matriz",
                EjercicioFormaPalabra.class, "redirect:/braille/lecciones/forma-palabras",
                EjercicioTraduccionSenia.class, "redirect:/senias"
        );

        if (vidas == 0) {
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
        Long usuarioId = (Long) request.getSession().getAttribute("id");
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
        if (resuelto){
            this.servicioExperiencia.ganar100DeExperiencia(usuarioId);
        } else {

            this.servicioVida.perderUnaVida(usuarioId);
        }

        this.servicioProgresoLeccion.actualizarProgreso(progreso, resuelto);

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
    public ModelAndView usarAyuda(@RequestParam("leccion") Long leccionId, @PathVariable("indice") Integer indice, HttpServletRequest request, Model model) {
        ModelMap modelo = new ModelMap();
        Long usuarioId = (Long) request.getSession().getAttribute("id");
        Integer vidas = this.servicioVida.obtenerVida(usuarioId).getCantidadDeVidasActuales();
        modelo.put("leccion", leccionId);
        Leccion leccion = this.servicioLeccion.obtenerLeccion(leccionId);
        Ejercicio ejercicio = leccion.getEjercicios().get(indice - 1);
        modelo.put("vidas", vidas);
        modelo.put("ejercicio", ejercicio);
        modelo.put("indice", indice);
        agregarTiempoRestanteAlModelo(modelo, usuarioId);

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
        public ModelAndView irAEjercicioVideo (@RequestParam(required = false, defaultValue = "2", value = "id") Long id)
        {
            ModelMap modelo = new ModelMap();
            Ejercicio ejercicioVideo = servicioEjercicio.obtenerEjercicio(id);
            modelo.put("ejercicio", ejercicioVideo);
            return new ModelAndView("ejercicio-video", modelo);
        }

        private void agregarTiempoRestanteAlModelo (ModelMap modelo, Long usuarioId){
            Vida vida = this.servicioVida.obtenerVida(usuarioId);
            LocalDateTime ahora = LocalDateTime.now();
            Duration duracion = Duration.between(vida.getUltimaVezQueSeRegeneroLaVida(), ahora);
            long segundosDesdeUltimaRegeneracion = duracion.getSeconds();
            long tiempoRestante = 60 - (segundosDesdeUltimaRegeneracion % 60); // Cada 60 segundos
            modelo.put("tiempoRestante", tiempoRestante);
        }
    }

