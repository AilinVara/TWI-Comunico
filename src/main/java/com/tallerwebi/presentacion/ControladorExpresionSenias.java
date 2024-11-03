
package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ExpresionSenias;
import com.tallerwebi.dominio.ServicioExpresion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hsqldb.lib.StringUtil.isEmpty;

@Controller
public class ControladorExpresionSenias {

    private ServicioExpresion servicioExpresion;
    private ArrayList<String> routes;
    private ArrayList<String> names;

    @Autowired
    public ControladorExpresionSenias(ServicioExpresion servicioExpresion) {
        this.servicioExpresion = servicioExpresion;
        routes = new ArrayList<>();
        names = new ArrayList<>();

        agregarRutas("src/main/webapp/resources/core/img/Como-estas-senias.png");
        agregarNombres("Como estas");

    }

    public void agregarRutas(String ruta) {
        routes.add(ruta);
    }
    public void agregarNombres(String nombre) {
        names.add(nombre);
    }

    @RequestMapping(path = "/expresiones", method = RequestMethod.GET)
    public ModelAndView cargarImagenesDeExpresion() throws IOException {
        List<ExpresionSenias> expresions = this.servicioExpresion.listarExpresionSenias();

        if(expresions.isEmpty()) {
            for(int i = 0; i < routes.size() &&  i < names.size(); i++) {
                this.servicioExpresion.guardarExpresionSenias(names.get(i), routes.get(i));
            }
        }
        return new ModelAndView("indice");
    }

@RequestMapping(value = "/img/senias/{nombre}")
public ResponseEntity<byte[]>obtenerImagenExpresionSenias(@PathVariable("nombre") String nombre) throws IOException {
        ExpresionSenias expresion = this.servicioExpresion.buscarExpresionSenias(nombre);
        byte[] imagen = expresion.getImagenExpresion();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imagen);
}


    @RequestMapping("senias/expresiones")
    public ModelAndView mostrarExpresionSenias(
            @RequestParam(required=false, defaultValue = "", value = "expresionsenias") String expresionBuscada) {
        ModelMap modelo = new ModelMap();
        List<ExpresionSenias> expresiones = new ArrayList<>();

        if(isEmpty(expresionBuscada)) {
            expresiones = this.servicioExpresion.listarExpresionSenias();
            modelo.put("expresionsenias", expresiones);
            return new ModelAndView("expresiones", modelo);
        }else{
            ExpresionSenias expresion = this.servicioExpresion.buscarExpresionSenias(expresionBuscada);
            if(expresion == null) {
                expresiones = this.servicioExpresion.listarExpresionSenias();
            }else{
                expresiones.add(expresion);
            }
            modelo.put("expresionsenias", expresiones);
            return new ModelAndView("expresiones", modelo);
        }
    }
}
