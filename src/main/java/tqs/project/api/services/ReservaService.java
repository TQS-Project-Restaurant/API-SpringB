package tqs.project.api.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import tqs.project.api.models.Reserva;

public interface ReservaService {
    List<LocalTime> getAvailableSlots(LocalDate date);

    Reserva createBooking(Reserva reserva);

    List<Reserva> getUserBookings(String email);
}
