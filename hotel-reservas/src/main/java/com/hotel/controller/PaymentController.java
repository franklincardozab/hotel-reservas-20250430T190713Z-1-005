package com.hotel.controller;

import com.hotel.service.PaymentService;
import com.hotel.service.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pago")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final ReservaService reservaService;

    @GetMapping("/checkout/{reservaId}")
    public String iniciarPago(@PathVariable Long reservaId, Model model) {
        Double monto = 100.0; // Monto de prueba, puedes obtenerlo de la BD
        try {
            String urlPago = paymentService.crearSesionPago(reservaId, monto);
            return "redirect:" + urlPago;
        } catch (Exception e) {
            model.addAttribute("error", "Error al procesar el pago.");
            return "error";
        }
    }

    @GetMapping("/exito")
    public String pagoExitoso(@RequestParam Long reservaId) {
        reservaService.confirmarReserva(reservaId);
        return "redirect:/reservas";
    }

    @GetMapping("/cancelado")
    public String pagoCancelado() {
        return "error";
    }
}

