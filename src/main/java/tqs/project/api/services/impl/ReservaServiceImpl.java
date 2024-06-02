package tqs.project.api.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tqs.project.api.dao.ReservaRequest;
import tqs.project.api.models.Reserva;
import tqs.project.api.models.Utilizador;
import tqs.project.api.others.Restaurant;
import tqs.project.api.others.STATUS;
import tqs.project.api.repositories.ReservaRepository;
import tqs.project.api.repositories.UtilizadorRepository;
import tqs.project.api.services.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService{
    
    private final ReservaRepository reservaRepository;
    private final UtilizadorRepository utilizadorRepository;
    private final Restaurant restaurant = new Restaurant();

    public ReservaServiceImpl(ReservaRepository reservaRepository, UtilizadorRepository utilizadorRepository){
        this.reservaRepository = reservaRepository;
        this.utilizadorRepository = utilizadorRepository;
    }


    @Override
    public List<LocalTime> getAvailableSlots(LocalDate date) {
        Restaurant restaurant = new Restaurant();

        List<LocalTime> availableSlots = restaurant.getDailySlots();
        List<Reserva> bookings = reservaRepository.findByDia(date);
        List<LocalTime> slotsToRemove = new ArrayList<>();

        int usedTables = 0;

        for (LocalTime hour : availableSlots){
            for (Reserva reserva : bookings) {
                if (reserva.getHora() == hour){
                    usedTables += reserva.getQuantidadeMesas();
                }
            }
            if (usedTables == restaurant.getTotalTables()){
                slotsToRemove.add(hour);
            }
        }

        availableSlots.removeAll(slotsToRemove);

        return availableSlots;
    }

    
    @Override
    public Reserva createBooking(ReservaRequest reserva, String email){
        List<Reserva> bookings = reservaRepository.findByDiaAndHora(reserva.getDia(), reserva.getHora());
        int occupiedTables = 0;

        for (Reserva booking : bookings) {
            occupiedTables += booking.getQuantidadeMesas();
        }

        // Verify if there is space for customers
        if (occupiedTables + reserva.getQuantidadeMesas() > restaurant.getTotalTables()){
            return null;
        }

        // creating and saving reserva
        Reserva bookingToSave = new Reserva();
        bookingToSave.setDia(reserva.getDia());
        bookingToSave.setHora(reserva.getHora());
        bookingToSave.setQuantidadeMesas(reserva.getQuantidadeMesas());
        bookingToSave.setStatus(STATUS.PENDING.ordinal());

        Utilizador utilizador = utilizadorRepository.findByEmail(email).get();
        bookingToSave.setUtilizador(utilizador);

        Reserva saved = reservaRepository.save(bookingToSave);

        // add RESERVA to UTILIZADOR
        if (utilizador.getReservas() == null){
            utilizador.setReservas(new ArrayList<>());
        }

        utilizador.getReservas().add(saved);
        utilizadorRepository.save(utilizador);

        return saved;
    }


    @Override
    public List<Reserva> getUserBookings(String email) {
        return reservaRepository.findByUtilizador(utilizadorRepository.findByEmail(email).orElse(null));
    }


    @Override
    public List<Reserva> getPendingBookings() {
        return reservaRepository.findAllByStatus(STATUS.PENDING.ordinal());
    }
}
