package tqs.project.api.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.api.services.ReservaService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService){
        this.reservaService = reservaService;
    }

    @GetMapping("availableSlots")
    public ResponseEntity<List<LocalTime>> getTimetable(@RequestParam("date") String date){
        LocalDate bookingDate = LocalDate.parse(date);

        List<LocalTime> availableSlots = reservaService.getAvailableSlots(bookingDate);

        return new ResponseEntity<>(availableSlots, HttpStatus.OK);
    }
}
