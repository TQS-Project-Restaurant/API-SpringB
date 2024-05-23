package tqs.project.api.services.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import tqs.project.api.models.Reserva;
import tqs.project.api.others.Restaurant;
import tqs.project.api.repositories.ReservaRepository;
import tqs.project.api.services.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService{
    
    private final ReservaRepository reservaRepository;

    public ReservaServiceImpl(ReservaRepository reservaRepository){
        this.reservaRepository = reservaRepository;
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
}
