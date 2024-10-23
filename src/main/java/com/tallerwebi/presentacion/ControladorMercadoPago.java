package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ControladorMercadoPago {
    @Value("${codigo.mercadoLibre}")
    private String mercadoLibreToken;

    public ControladorMercadoPago() {

    }

    @RequestMapping(value = "/mp/{cantidad}", method = RequestMethod.POST)
    public ResponseEntity<String> irAMercadoPago(@PathVariable("cantidad") Integer cantidad, HttpServletRequest request) {

        Map<String, String> customHeaders = new HashMap<>();
        customHeaders.put("x-idempotency-key", <SOME_UNIQUE_VALUE>);

        MPRequestOptions requestOptions = MPRequestOptions.builder()
                .customHeaders(customHeaders)
                .build();

        MercadoPagoConfig.setAccessToken("YOUR_ACCESS_TOKEN");

        PaymentClient client = new PaymentClient();

        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .transactionAmount(request.getTransactionAmount())
                        .token(request.getToken())
                        .description(request.getDescription())
                        .installments(request.getInstallments())
                        .paymentMethodId(request.getPaymentMethodId())
                        .payer(
                                PaymentPayerRequest.builder()
                                        .email(request.getPayer().getEmail())
                                        .firstName(request.getPayer().getFirstName())
                                        .identification(
                                                IdentificationRequest.builder()
                                                        .type(request.getPayer().getIdentification().getType())
                                                        .number(request.getPayer().getIdentification().getNumber())
                                                        .build())
                                        .build())
                        .build();

        client.create(paymentCreateRequest, requestOptions);


    }

    @RequestMapping("/compra/{resultado}")
    public ModelAndView compra(@PathVariable(value = "resultado", required = false) String resultado){
        ModelMap modelo = new ModelMap();

        if(resultado.equals("success"))
            modelo.put("mensaje", "compra hecha");
        else if (resultado.equals("pending"))
            modelo.put("mensaje", "compra pendiente");
        else if(resultado.equals("failure"))
            modelo.put("mensaje", "compra fallida");

        return new ModelAndView("mercadopago", modelo);
    }
}
