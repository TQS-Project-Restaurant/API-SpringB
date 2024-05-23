package tqs.project.api.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaService {
    List<LocalTime> getAvailableSlots(LocalDate date);
}
