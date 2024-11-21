package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.Vida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ControladorTienda {

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorTienda(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

//    @RequestMapping(value = "/mp/{cantidad}", method = RequestMethod.POST)
//    public ResponseEntity<String> irAMercadoPago(@PathVariable("cantidad") Integer cantidad, @PathVariable("precio") Integer precio) {
//        try {
//            MercadoPagoConfig.setAccessToken("APP_USR-784455809696373-102309-0ae967099b25c21008808523c49a20d5-2050594623");
//
//            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
//                    .title("Comunico Points")
//                    .quantity(cantidad)
//                    .unitPrice(new BigDecimal(precio))
//                    .currencyId("ARS")
//                    .build();
//
//            List<PreferenceItemRequest> items = new ArrayList<>();
//            items.add(itemRequest);
//
//            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
//                    .builder()
//                    .success("http://localhost:8080/comprar?quantity=" + cantidad)
//                    .pending("http://localhost:8080/comprar?quantity=" + cantidad)
//                    .failure("http://localhost:8080/comprar?quantity=" + cantidad)
//                    .build();
//
//
//            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
//                    .items(items)
//                    .backUrls(backUrls)
//                    .build();
//
//            PreferenceClient client = new PreferenceClient();
//            Preference preference = client.create(preferenceRequest);
//
//            return ResponseEntity.ok(preference.getId());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
//
//    @RequestMapping("/comprar")
//    public ModelAndView compra(@RequestParam Map<String, String> allParams, HttpServletRequest request) {
//        ModelMap modelo = new ModelMap();
//
//        String resultado = allParams.get("status");
//        String cantidadParam = allParams.get("quantity");
//
//        Long idUsuario = (Long) request.getSession().getAttribute("id");
//        if (resultado != null && cantidadParam != null) {
//            if (resultado.equals("approved")) {
//                Integer cantidad = Integer.parseInt(cantidadParam);
//                Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
//                usuario.setComunicoPoints(usuario.getComunicoPoints() + cantidad);
//                this.servicioUsuario.modificar(usuario);
//                request.getSession().setAttribute("points", usuario.getComunicoPoints());
//                modelo.put("points", usuario.getComunicoPoints());
//                modelo.put("alerta", "Compra realizada satisfactoriamente.");
//                modelo.put("tipoAlerta", "success");
//            } else {
//                modelo.put("alerta", "No pudimos procesar la compra.");
//                modelo.put("tipoAlerta", "error");
//            }
//
//        }
//        return new ModelAndView("comprarPoints", modelo);
//    }

    @RequestMapping("/tienda")
    public ModelAndView tienda(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        Long idUsuario = (Long) request.getSession().getAttribute("id");
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

        Integer saldo = usuario.getComunicoPoints();

        modelo.put("saldo", saldo);

        return new ModelAndView("tienda", modelo);
    }

    @PostMapping("/comprarComunicoPoints")
    public ModelAndView comprarComunicoPoints(@RequestParam("cantidad") Integer cantidad, RedirectAttributes flash) {
        Double precio = 0.0d;

        switch (cantidad) {
            case 500:
                precio = 5000D;
                break;
            case 1000:
                precio = 10000D;
                break;
            case 3000:
                precio = 30000D;
                break;
        }

        try {
            MercadoPagoConfig.setAccessToken("APP_USR-784455809696373-102309-0ae967099b25c21008808523c49a20d5-2050594623");

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(cantidad + " ComunicoPoints")
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(precio))
                    .currencyId("ARS")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/comprarComunicoPoints/Pagar?collection_status=approved&cantidad=" + cantidad)
                    .failure("http://localhost:8080/comprarComunicoPoints/Pagar?collection_status=failure&cantidad=" + cantidad)
                    .pending("http://localhost:8080/comprarComunicoPoints/Pagar?collection_status=pending&cantidad=" + cantidad)
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return new ModelAndView("redirect:" + preference.getSandboxInitPoint());
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Hubo un problema al procesar la compra.");
        }

        return new ModelAndView("redirect:/tienda");
    }


    @GetMapping("/comprarComunicoPoints/Pagar")
    public ModelAndView PagarComunicoPoints(@RequestParam Map<String, String> allParams, RedirectAttributes flash, HttpServletRequest request) {

        String resultado = allParams.get("status");
        Long idUsuario = (Long) request.getSession().getAttribute("id");
        Integer cantidadParam = Integer.valueOf(allParams.get("cantidad"));

        if (resultado.equals("approved")) {
            Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
            usuario.setComunicoPoints(usuario.getComunicoPoints() + cantidadParam);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("points", usuario.getComunicoPoints());
            flash.addFlashAttribute("success", "El pago fue aprobado exitosamente.");
        } else {
            flash.addFlashAttribute("error", "El pago no fue aprobado.");
        }

        return new ModelAndView("redirect:/tienda");
    }

    @PostMapping("/comprarVidas")
    public ModelAndView comprarVidas(@RequestParam("vidas") Integer vidas, HttpServletRequest request, RedirectAttributes flash) {
        ModelMap modelo = new ModelMap();

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

        Integer saldoUsuario = usuario.getComunicoPoints();
        Vida vida = usuario.getVida();
        Integer cantidadActual = vida.getCantidadDeVidasActuales();
        final int LIMITE_VIDAS = 5;
        Integer precio = 0;

        switch (vidas) {
            case 1: precio = 200;
                    break;
            case 3: precio = 500;
                    break;
            case 5: precio = 800;
                    break;
        }

        if (cantidadActual + vidas > LIMITE_VIDAS) {
            flash.addFlashAttribute("error", "La cantidad seleccionada excede el límite de " + LIMITE_VIDAS + " vidas. Por favor, seleccioná una cantidad válida.");
            return new ModelAndView("redirect:/tienda");
        }

        if (saldoUsuario >= precio) {
            Integer nuevoSaldo = saldoUsuario - precio;
            usuario.setComunicoPoints(nuevoSaldo);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("points", usuario.getComunicoPoints());
            vida.setCantidadDeVidasActuales(cantidadActual + vidas);
            usuario.setVida(vida);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("vidasNumero", usuario.getVida().getCantidadDeVidasActuales());

            flash.addFlashAttribute("success", "La compra fue realizada con éxito. Ahora tenés " + vida.getCantidadDeVidasActuales() + " vidas.");
        } else {
            flash.addFlashAttribute("error", "Saldo insuficiente. Necesitás " + (precio - saldoUsuario) + " ComunicoPoints más.");
        }

        return new ModelAndView("redirect:/tienda");
    }

    @PostMapping("/comprarAyudas")
    public ModelAndView comprarAyudas(@RequestParam("ayudas") Integer ayudas, HttpServletRequest request, RedirectAttributes flash) {
        Long idUsuario = (Long) request.getSession().getAttribute("id");
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

        Integer saldoUsuario = usuario.getComunicoPoints();

        Integer precio = 0;

        switch (ayudas) {
            case 1: precio = 800;
                break;
            case 3: precio = 1000;
                break;
            case 5: precio = 1500;
                break;
        }

        if (saldoUsuario >= precio) {
            Integer nuevoSaldo = saldoUsuario - precio;
            usuario.setComunicoPoints(nuevoSaldo);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("points", usuario.getComunicoPoints());
            usuario.setAyudas(usuario.getAyudas() + ayudas);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("ayudas", usuario.getAyudas());

            flash.addFlashAttribute("success", "La compra fue realizada con éxito. Ahora tenés " + usuario.getAyudas() + " ayudas.");
        } else {
            flash.addFlashAttribute("error", "Saldo insuficiente. Necesitás " + (precio - saldoUsuario) + " ComunicoPoints más.");
        }

        return new ModelAndView("redirect:/tienda");
    }

    @PostMapping("/comprarLlaves")
    public ModelAndView comprarLlaves(@RequestParam("llaves") Integer llaves, HttpServletRequest request, RedirectAttributes flash) {
        Long idUsuario = (Long) request.getSession().getAttribute("id");
        Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);

        Integer saldoUsuario = usuario.getComunicoPoints();

        Integer precio = 0;

        switch (llaves) {
            case 1: precio = 1000;
                break;
            case 3: precio = 1500;
                break;
            case 5: precio = 3000;
                break;
        }

        if (saldoUsuario >= precio) {
            Integer nuevoSaldo = saldoUsuario - precio;
            usuario.setComunicoPoints(nuevoSaldo);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("points", usuario.getComunicoPoints());
            usuario.setLlaves(usuario.getLlaves() + llaves);
            this.servicioUsuario.modificar(usuario);
            request.getSession().setAttribute("llaves", usuario.getLlaves());

            flash.addFlashAttribute("success", "La compra fue realizada con éxito. Ahora tenés " + usuario.getLlaves() + " llaves.");
        } else {
            flash.addFlashAttribute("error", "Saldo insuficiente. Necesitás " + (precio - saldoUsuario) + " ComunicoPoints más.");
        }

        return new ModelAndView("redirect:/tienda");
    }
}
