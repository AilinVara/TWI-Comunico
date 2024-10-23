package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.ServicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
public class ControladorMercadoPago {

    @Value("${codigo.mercadoLibre}")
    private String mercadoLibreToken;

    private ServicioUsuario servicioUsuario;

    @Autowired
    public ControladorMercadoPago(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @RequestMapping(value = "/mp/{cantidad}", method = RequestMethod.POST)
    public ResponseEntity<String> irAMercadoPago(@PathVariable("cantidad") Integer cantidad) {
        try {
            MercadoPagoConfig.setAccessToken("APP_USR-784455809696373-102309-0ae967099b25c21008808523c49a20d5-2050594623");

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title("Comunico Points")
                    .quantity(cantidad)
                    .unitPrice(new BigDecimal(10))
                    .currencyId("ARS")
                    .build();

            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
                    .builder()
                    .success("http://localhost:8080/comprar?quantity="+cantidad)
                    .pending("http://localhost:8080/comprar?quantity="+cantidad)
                    .failure("http://localhost:8080/comprar?quantity="+cantidad)
                    .build();


            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return ResponseEntity.ok(preference.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping("/comprar")
    public ModelAndView compra(@RequestParam Map<String, String> allParams, HttpServletRequest request){
        ModelMap modelo = new ModelMap();

        String resultado = allParams.get("status");
        String cantidadParam = allParams.get("quantity");

        Long idUsuario = (Long) request.getSession().getAttribute("id");
        if(resultado != null && cantidadParam != null){
            if(resultado.equals("approved")){
                Integer cantidad = Integer.parseInt(cantidadParam);
                Usuario usuario = this.servicioUsuario.buscarUsuarioPorId(idUsuario);
                usuario.setComunicoPoints(usuario.getComunicoPoints() + cantidad);
                this.servicioUsuario.modificar(usuario);
                modelo.put("resultado", resultado);
            }
            else if (resultado.equals("pending"))
                modelo.put("resultado", resultado);
            else if(resultado.equals("rejected"))
                modelo.put("resultado", resultado);
        }
        return new ModelAndView("comprarPoints", modelo);
    }
}
