package com.hotel.controller;

import com.hotel.service.PagoService;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/pago")
    public RedirectView realizarPago(@RequestParam String descripcion,
                                     @RequestParam double precio) throws StripeException {
        String exitoURL = "http://localhost:8080/pago/exito";
        String falloURL = "http://localhost:8080/pago/fallo";

        String urlPago = pagoService.crearSesionPago(descripcion, precio, "usd", exitoURL, falloURL);
        return new RedirectView(urlPago);
    }

    @GetMapping("/pago/exito")
    public String pagoExitoso() {
        return "pago-exitoso";
    }

    @GetMapping("/pago/fallo")
    public String pagoFallido() {
        return "pago-fallido";
    }
}
