package com.hotel.service;

import com.hotel.model.Reserva;
import com.hotel.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public void guardarReserva(Reserva reserva) {
        reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerReservasPorUsuario(String email) {
        return reservaRepository.findByUsuarioEmail(email);
    }
}
