package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.Matriz;
import com.tallerwebi.dominio.ServicioEjercicio;
import com.tallerwebi.dominio.ServicioMatriz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;
    private ServicioMatriz servicioMatriz;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio, ServicioMatriz servicioMatriz) {
        this.servicioEjercicio = servicioEjercicio;
        this.servicioMatriz = servicioMatriz;
    }


//    @RequestMapping(value = "/ejercicio", method = RequestMethod.GET)
//    public ModelAndView irAjercicio(){
//        ModelMap modelo = new ModelMap();
//        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(1L);
//        modelo.put("ejercicio", ejercicio);
//        return new ModelAndView("ejercicio", modelo);
//    }

    @RequestMapping(value = "braille/ejercicio", method = RequestMethod.GET)
    public ModelAndView irAEjercicio(@RequestParam(required = false, defaultValue = "1", value = "id") Long id){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(id);
        modelo.put("ejercicio", ejercicio);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping( path = "/resolver", method = RequestMethod.POST)
    public ModelAndView resolverEjercicio(@RequestParam("opcionSeleccionada") Long opcionId, @RequestParam("ejercicioId")Long ejercicioId){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Boolean resuelto = this.servicioEjercicio.resolverEjercicio(ejercicio, opcionId);
        modelo.put("ejercicio",ejercicio);
        modelo.put("esCorrecta", (resuelto));
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping(value = "braille/formaLetras", method = RequestMethod.GET)
    public ModelAndView irAFormaLetras(@RequestParam(required = false, defaultValue = "10", value = "id") Long idEjercicio,
                                       @RequestParam(required = false, defaultValue = "1", value = "id") Long idMatriz){
        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = servicioEjercicio.obtenerEjercicio(idEjercicio);
        Matriz matriz = servicioMatriz.obtenerMatriz(idMatriz);
        modelo.put("ejercicio", ejercicio);
        modelo.put("matriz", matriz);
        return new ModelAndView("formaLetras", modelo);
    }

    @RequestMapping(path = "braille/formaLetras/resolver", method = RequestMethod.POST)
    public ModelAndView resolverMatriz(
            @RequestParam("puntosSeleccionados") String puntosSeleccionados,
            @RequestParam("ejercicioId")Long ejercicioId,
            @RequestParam("matrizId")Long matrizId) {

        ModelMap modelo = new ModelMap();
        Ejercicio ejercicio = this.servicioEjercicio.obtenerEjercicio(ejercicioId);
        Matriz matriz = this.servicioMatriz.obtenerMatriz(matrizId);
        Boolean resuelto = this.servicioMatriz.resolverMatriz(puntosSeleccionados, matriz.getPuntos());
        modelo.put("esCorrecta", resuelto);

        if (resuelto) {
            if (ejercicioId < 12) {
                Ejercicio siguienteEjercicio = this.servicioEjercicio.obtenerEjercicio(ejercicio.getId() + 1);
                Matriz siguienteMatriz = this.servicioMatriz.obtenerMatriz(matrizId + 1);
                modelo.put("matriz", siguienteMatriz);
                modelo.put("ejercicio", siguienteEjercicio);
            } else if (ejercicioId == 12) {
                modelo.put("matriz", matriz);
                modelo.put("mostrarVolverMenu", true);
                modelo.put("ejercicio", ejercicio);
            }
        } else {
            modelo.put("matriz", matriz);
            modelo.put("ejercicio", ejercicio);
        }
        return new ModelAndView("formaLetras", modelo);
    }
}
