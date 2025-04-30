package com.hotel.controller;

import com.hotel.model.Reserva;
import com.hotel.model.Usuario;
import com.hotel.service.HotelService;
import com.hotel.service.ReservaService;
import com.hotel.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;
    private final UsuarioService usuarioService;
    private final HotelService hotelService;

    public ReservaController(ReservaService reservaService, UsuarioService usuarioService, HotelService hotelService) {
        this.reservaService = reservaService;
        this.usuarioService = usuarioService;
        this.hotelService = hotelService;
    }

    @GetMapping("/nueva/{hotelId}")
    public String mostrarFormularioReserva(@PathVariable Long hotelId, Model model) {
        model.addAttribute("hotel", hotelService.obtenerHotelPorId(hotelId));
        return "reserva-form";
    }

    @PostMapping("/nueva")
    public String hacerReserva(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam Long hotelId,
                               @RequestParam String fechaEntrada,
                               @RequestParam String fechaSalida,
                               @RequestParam int numeroPersonas) {
        
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(userDetails.getUsername());
        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setHotel(hotelService.obtenerHotelPorId(hotelId));
        reserva.setFechaEntrada(LocalDate.parse(fechaEntrada));
        reserva.setFechaSalida(LocalDate.parse(fechaSalida));
        reserva.setNumeroPersonas(numeroPersonas);

        double precioPorNoche = reserva.getHotel().getPrecioPorNoche();
        int dias = reserva.getFechaSalida().compareTo(reserva.getFechaEntrada());
        reserva.setPrecioTotal(precioPorNoche * dias * numeroPersonas);

        reservaService.guardarReserva(reserva);
        return "redirect:/reservas/mis-reservas";
    }

    @GetMapping("/mis-reservas")
    public String listarReservas(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("reservas", reservaService.obtenerReservasPorUsuario(userDetails.getUsername()));
        return "mis-reservas";
    }
}
