package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.ServicioSuscripcion;
import com.tallerwebi.dominio.excepcion.SuscriptoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorSuscripcion {

    private final ServicioSuscripcion servicioSuscripcion;
    private final ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorSuscripcion(ServicioSuscripcion servicioSuscripcion, ServicioUsuario servicioUsuario) {
        this.servicioSuscripcion = servicioSuscripcion;
        this.servicioUsuario = servicioUsuario;
    }

    @GetMapping("/suscripciones")
    public ModelAndView planes(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("id") != null) {
            ModelMap modelo = new ModelMap();
            modelo.addAttribute("sin plan", servicioSuscripcion.descripcionSuscripciones(1L));
            modelo.addAttribute("basico", servicioSuscripcion.descripcionSuscripciones(2L));
            modelo.addAttribute("estandar", servicioSuscripcion.descripcionSuscripciones(3L));
            modelo.addAttribute("premium", servicioSuscripcion.descripcionSuscripciones(4L));

            return new ModelAndView("suscripciones", modelo);
        }
        return new ModelAndView("redirect:/home");
    }

    @PostMapping("/comprarSuscripcion")
    public ModelAndView comprarPlan(@RequestParam("nombreSuscripcion") String nombreSuscripcion, HttpServletRequest request, RedirectAttributes flash) {
        HttpSession session = request.getSession();
        Double precio = 0.0d;

        if(!session.getAttribute("nombreSuscripcion").equals("sin plan")) {
            flash.addFlashAttribute("error", "El usuario ya tiene una suscripción activa.");
            return new ModelAndView("redirect:/suscripciones");
        }

        switch (nombreSuscripcion.toLowerCase()) {
            case "basico":
                precio = 3000D;
                break;
            case "estandar":
                precio = 5000D;
                break;
            case "premium":
                precio = 8000D;
                break;
        }

        if (session.getAttribute("nombreSuscripcion").equals("sin plan")) {
            try {
                MercadoPagoConfig.setAccessToken("APP_USR-784455809696373-102309-0ae967099b25c21008808523c49a20d5-2050594623");

                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .title(nombreSuscripcion + " Suscripcion")
                        .quantity(1)
                        .unitPrice(BigDecimal.valueOf(precio))
                        .currencyId("ARS")
                        .build();

                List<PreferenceItemRequest> items = new ArrayList<>();
                items.add(itemRequest);

                PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                        .success("http://localhost:8080/comprarSuscripcion/Pagar?collection_status=approved&nombreSuscripcion=" + nombreSuscripcion)
                        .failure("http://localhost:8080/comprarSuscripcion/Pagar?collection_status=failure&nombreSuscripcion=" + nombreSuscripcion)
                        .pending("http://localhost:8080/comprarSuscripcion/Pagar?collection_status=pending&nombreSuscripcion=" + nombreSuscripcion)
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
        } else {
            flash.addFlashAttribute("error", "El usuario ya tiene una suscripcion activa.");
        }
        return new ModelAndView("redirect:/suscripciones");
    }

    @GetMapping("/comprarSuscripcion/Pagar")
    public ModelAndView feedback(@RequestParam("nombreSuscripcion") String nombreSuscripcion,
                                 @RequestParam Map<String, String> allParams,HttpServletRequest request, RedirectAttributes flash) {
        HttpSession session = request.getSession();
        String resultado = allParams.get("status");

        Long idUsuario = (Long) request.getSession().getAttribute("id");

        if (resultado.equals("approved")) {
            Usuario usuario = servicioUsuario.buscarUsuarioPorId(idUsuario);

            switch (nombreSuscripcion) {
                case "basico":
                    beneficiosPlanBasico(usuario, flash);
                    break;
                case "estandar":
                    beneficiosPlanEstandar(usuario, flash);
                    break;
                case "premium":
                    beneficiosPlanPremium(usuario, flash);
                    break;
            }
            session.setAttribute("nombreSuscripcion", usuario.getSuscripcion().getTipoSuscripcion().getNombre());
            flash.addFlashAttribute("success", "El pago fue aprobado exitosamente.");
        } else {
            flash.addFlashAttribute("error", "El pago no fue aprobado.");
        }

        return new ModelAndView("redirect:/suscripciones");
    }

    private void beneficiosPlanPremium(Usuario usuario, RedirectAttributes flash) {
        try {
            servicioSuscripcion.comprarSuscripcionPremium(usuario);
        } catch (SuscriptoException e) {
            flash.addFlashAttribute("error", "El usuario ya tiene una suscripción activa.");
        }
    }

    private void beneficiosPlanEstandar(Usuario usuario, RedirectAttributes flash) {
        try {
            servicioSuscripcion.comprarSuscripcionEstandar(usuario);
        } catch (SuscriptoException e) {
            flash.addFlashAttribute("error", "El usuario ya tiene una suscripción activa.");
        }
    }

    private void beneficiosPlanBasico(Usuario usuario, RedirectAttributes flash) {
        try {
            servicioSuscripcion.comprarSuscripcionBasica(usuario);
        } catch (SuscriptoException e) {
            flash.addFlashAttribute("error", "El usuario ya tiene una suscripción activa.");
        }
    }
}